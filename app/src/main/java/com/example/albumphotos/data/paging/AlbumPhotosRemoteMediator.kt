import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.albumphotos.data.local.AlbumPhotosDatabase
import com.example.albumphotos.data.remote.AlbumPhotosApi
import com.example.albumphotos.model.AlbumPhotosImage
import com.example.albumphotos.model.AlbumPhotosRemoteKeys
import com.example.albumphotos.util.Constants.ITEMS_PER_PAGE


@ExperimentalPagingApi
class AlbumPhotosRemoteMediator(
    private val albumPhotosApi: AlbumPhotosApi,
    private val albumPhotosDatabase:  AlbumPhotosDatabase
) : RemoteMediator<Int, AlbumPhotosImage>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AlbumPhotosImage>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = albumPhotosApi.getAllImages(page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            albumPhotosDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    albumPhotosDatabase.albumPhotosImageDao().deleteAllImages()
                    albumPhotosDatabase.albumPhotosRemoteKeysDao().deleteAllRemoteKeys()
                }
                val keys = response.map { albumPhotosImage ->
                    AlbumPhotosRemoteKeys(
                        id = albumPhotosImage.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                albumPhotosDatabase.albumPhotosRemoteKeysDao().addAllRemoteKeys(remoteKeys = keys)
                albumPhotosDatabase.albumPhotosImageDao().addImages(images = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int,  AlbumPhotosImage>
    ): AlbumPhotosRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                albumPhotosDatabase.albumPhotosRemoteKeysDao().getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, AlbumPhotosImage>
    ):  AlbumPhotosRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { albumPhotosImage ->
                albumPhotosDatabase.albumPhotosRemoteKeysDao().getRemoteKeys(id = albumPhotosImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, AlbumPhotosImage>
    ): AlbumPhotosRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                albumPhotosDatabase.albumPhotosRemoteKeysDao().getRemoteKeys(id = unsplashImage.id)
            }
    }

}