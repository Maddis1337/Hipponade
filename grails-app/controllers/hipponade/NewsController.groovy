package hipponade

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class NewsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def allNews = News.list(sort: 'date', order: 'desc')
        def usedTags = new HashSet()
        for (news in allNews) {
            if (!news.tags) continue
            for (tag in news.tags) {
                usedTags.add(tag)
            }
        }
        respond allNews, model: [newsInstanceCount: News.count(), usedTags: usedTags]
    }

    def show(News newsInstance) {
        respond newsInstance
    }

    def create() {
        respond new News(params)
    }

    @Transactional
    def save(News newsInstance) {
        if (newsInstance == null) {
            notFound()
            return
        }

        if (newsInstance.hasErrors()) {
            respond newsInstance.errors, view:'create'
            return
        }

        newsInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'news.label', default: 'News'), newsInstance.id])
                redirect newsInstance
            }
            '*' { respond newsInstance, [status: CREATED] }
        }
    }

    def edit(News newsInstance) {
        respond newsInstance
    }

    @Transactional
    def update(News newsInstance) {
        if (newsInstance == null) {
            notFound()
            return
        }

        if (newsInstance.hasErrors()) {
            respond newsInstance.errors, view:'edit'
            return
        }

        newsInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'News.label', default: 'News'), newsInstance.id])
                redirect newsInstance
            }
            '*'{ respond newsInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(News newsInstance) {

        if (newsInstance == null) {
            notFound()
            return
        }

        newsInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'News.label', default: 'News'), newsInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'news.label', default: 'News'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
