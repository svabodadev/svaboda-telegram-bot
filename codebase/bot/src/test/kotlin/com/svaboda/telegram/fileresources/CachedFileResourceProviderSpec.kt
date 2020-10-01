package com.svaboda.telegram.fileresources

import com.svaboda.telegram.domain.ResourceProvider
import com.svaboda.telegram.domain.TelegramResource
import com.svaboda.telegram.fileresources.FileResourcesUtils.TEXTS_FILE_EXTENSION
import com.svaboda.telegram.fileresources.FileResourcesUtils.TEXTS_PATH
import com.svaboda.telegram.fileresources.FileResourcesUtils.cyrillicCommand
import com.svaboda.telegram.fileresources.FileResourcesUtils.cyrillicContentWithTopics
import com.svaboda.telegram.fileresources.FileResourcesUtils.topicsCommand
import com.svaboda.telegram.fileresources.FileResourcesUtils.topicsContent
import io.vavr.control.Try
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.lang.RuntimeException

class CachedFileResourceProviderSpec {

    private val fileResourcesProperties = FileResourcesProperties(TEXTS_PATH, TEXTS_FILE_EXTENSION)
    private lateinit var cachedFileResourceProviderIT: ResourceProvider<String>

    @Test
    fun `should return telegram resource with proper text content when default command provided`() {
        //given
        cachedFileResourceProviderIT = CachedFileResourceProvider(TextFileResourceReader(fileResourcesProperties))
        val defaultCommand = topicsCommand()

        //when
        val result = cachedFileResourceProviderIT.provideBy(defaultCommand).get()

        //then
        assertThat(result).isEqualTo(TelegramResource(topicsContent()))
    }

    @Test
    fun `should cache result of file resource reader function call`() {
        //given
        val command = cyrillicCommand()
        val resourceProvider = Mockito.mock(TextFileResourceReader::class.java)
        Mockito.`when`(resourceProvider.readFrom(command.resourceId()))
                .thenReturn(Try.success(cyrillicContentWithTopics()))
        cachedFileResourceProviderIT = CachedFileResourceProvider(resourceProvider)
        val numberOfCalls = 10

        //when
        for (call in 1..numberOfCalls) { cachedFileResourceProviderIT.provideBy(command) }

        //then
        Mockito.verify(resourceProvider, Mockito.times(1)).readFrom(ArgumentMatchers.any())
    }

    @Test
    fun `should return the same result when calling multiple times with the same command`() {
        //given
        cachedFileResourceProviderIT = CachedFileResourceProvider(TextFileResourceReader(fileResourcesProperties))
        val numberOfCalls = 10
        val command = cyrillicCommand()
        val expectedResult = TelegramResource(cyrillicContentWithTopics())

        //when
        val result = cachedFileResourceProviderIT.provideBy(command).get()

        //then
        for (call in 1..numberOfCalls) {
            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `should return return failure when underlying resources provider failed`() {
        //given
        val command = cyrillicCommand()
        val resourceProvider = Mockito.mock(TextFileResourceReader::class.java)
        Mockito.`when`(resourceProvider.readFrom(command.resourceId()))
                .thenReturn(Try.failure(RuntimeException()))

        cachedFileResourceProviderIT = CachedFileResourceProvider(resourceProvider)

        //when
        val result = cachedFileResourceProviderIT.provideBy(command)

        //then
        assertThat(result.isFailure).isTrue()
    }

}