package hipponade

class Tag {
    String label

    static hasMany = [events: Event, news: News, products: Product, recipes: Recipe]
    static constraints = {
        label blank: false, nullable: true, unique: true, minSize: 3, maxSize: 20
    }

    def String toString() {
        return label
    }
}
