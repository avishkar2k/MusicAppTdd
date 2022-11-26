package tdd.app.musicapp.playlist

import org.junit.Assert.assertEquals
import org.junit.Test
import tdd.app.musicapp.models.Playlist
import tdd.app.musicapp.models.PlaylistApiResponse
import tdd.app.musicapp.models.PlaylistMapper
import tdd.app.musicapp.utils.BaseUnitTest
import tdd.app.musicapp.R

class PlaylistMapperShould : BaseUnitTest() {

    private val playlistApiResponseRock = PlaylistApiResponse("1", "Hard Rock Cafa", "rock")

    private val playlistApiResponseDefault = PlaylistApiResponse("2", "Chilled House", "house")

    private val playlistApiResponseList: List<PlaylistApiResponse> = listOf(
        playlistApiResponseRock,
        playlistApiResponseDefault
    )

    private val mappedPlaylist: List<Playlist> = PlaylistMapper().invoke(playlistApiResponseList)

    private val playlistRock = mappedPlaylist[0]
    private val playlistDefault = mappedPlaylist[1]

    @Test
    fun haveSameIdForMappedPlaylist() {
        assertEquals(playlistApiResponseDefault.id, playlistDefault.id)
        assertEquals(playlistApiResponseRock.id, playlistRock.id)
    }

    @Test
    fun haveSameNameForMappedPlaylist() {
        assertEquals(playlistApiResponseDefault.name, playlistDefault.name)
        assertEquals(playlistApiResponseRock.name, playlistRock.name)
    }

    @Test
    fun haveSameCategoryForMappedPlaylist() {
        assertEquals(playlistApiResponseDefault.category, playlistDefault.category)
        assertEquals(playlistApiResponseRock.category, playlistRock.category)
    }

    @Test
    fun haveRockImageForRockCategory() {
        assertEquals(R.mipmap.rock, playlistRock.image)
    }

    @Test
    fun havePlaylistImageForCategoriesOtherThanRock() {
        assertEquals(R.mipmap.playlist, playlistDefault.image)
    }
}