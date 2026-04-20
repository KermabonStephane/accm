package com.accm.comicbook.application.service

import com.accm.comicbook.domain.model.ComicBook
import com.accm.comicbook.domain.model.ComicBookStatus
import com.accm.comicbook.domain.model.Series
import com.accm.comicbook.domain.model.SeriesStatus
import com.accm.comicbook.domain.port.out.ComicBookRepositoryPort
import com.accm.comicbook.domain.port.out.SeriesRepositoryPort
import spock.lang.Specification

class SeriesServiceSpec extends Specification {

    SeriesRepositoryPort seriesRepository = Mock()
    ComicBookRepositoryPort comicBookRepository = Mock()
    SeriesService service = new SeriesService(seriesRepository, comicBookRepository)

    def seriesId = UUID.randomUUID()
    def series = Series.builder().id(seriesId).name("Watchmen").status(SeriesStatus.ONGOING).build()

    def "createSeries assigns an id and defaults status to ONGOING"() {
        given:
        def request = Series.builder().name("Watchmen").build()
        seriesRepository.save(_) >> { Series s -> s }

        when:
        def result = service.createSeries(request)

        then:
        result.id() != null
        result.name() == "Watchmen"
        result.status() == SeriesStatus.ONGOING
    }

    def "createSeries preserves provided status"() {
        given:
        def request = Series.builder().name("V for Vendetta").status(SeriesStatus.COMPLETED).build()
        seriesRepository.save(_) >> { Series s -> s }

        when:
        def result = service.createSeries(request)

        then:
        result.status() == SeriesStatus.COMPLETED
    }

    def "getSeriesById returns the series"() {
        given:
        seriesRepository.findById(seriesId) >> Optional.of(series)

        when:
        def result = service.getSeriesById(seriesId)

        then:
        result.id() == seriesId
        result.name() == "Watchmen"
    }

    def "getSeriesById throws when not found"() {
        given:
        seriesRepository.findById(seriesId) >> Optional.empty()

        when:
        service.getSeriesById(seriesId)

        then:
        thrown(NoSuchElementException)
    }

    def "listSeries returns all series"() {
        given:
        seriesRepository.findAll() >> [series]

        when:
        def result = service.listSeries()

        then:
        result.size() == 1
        result[0].name() == "Watchmen"
    }

    def "updateSeries updates name, status and parentId"() {
        given:
        def update = Series.builder().name("Watchmen Vol.2").status(SeriesStatus.COMPLETED).build()
        seriesRepository.findById(seriesId) >> Optional.of(series)
        seriesRepository.save(_) >> { Series s -> s }

        when:
        def result = service.updateSeries(seriesId, update)

        then:
        result.id() == seriesId
        result.name() == "Watchmen Vol.2"
        result.status() == SeriesStatus.COMPLETED
    }

    def "updateSeries throws when not found"() {
        given:
        seriesRepository.findById(seriesId) >> Optional.empty()

        when:
        service.updateSeries(seriesId, Series.builder().name("X").status(SeriesStatus.ONGOING).build())

        then:
        thrown(NoSuchElementException)
    }

    def "deleteSeries deletes when found"() {
        given:
        seriesRepository.findById(seriesId) >> Optional.of(series)

        when:
        service.deleteSeries(seriesId)

        then:
        1 * seriesRepository.delete(seriesId)
    }

    def "deleteSeries throws when not found"() {
        given:
        seriesRepository.findById(seriesId) >> Optional.empty()

        when:
        service.deleteSeries(seriesId)

        then:
        thrown(NoSuchElementException)
        0 * seriesRepository.delete(_)
    }

    def "listComicBooks returns comicbooks of the series"() {
        given:
        def comicBook = ComicBook.builder().id(UUID.randomUUID()).title("Watchmen #1").status(ComicBookStatus.ACTIVE).seriesId(seriesId).build()
        seriesRepository.findById(seriesId) >> Optional.of(series)
        comicBookRepository.findBySeriesId(seriesId) >> [comicBook]

        when:
        def result = service.listComicBooks(seriesId)

        then:
        result.size() == 1
        result[0].title() == "Watchmen #1"
    }

    def "listComicBooks throws when series not found"() {
        given:
        seriesRepository.findById(seriesId) >> Optional.empty()

        when:
        service.listComicBooks(seriesId)

        then:
        thrown(NoSuchElementException)
    }
}
