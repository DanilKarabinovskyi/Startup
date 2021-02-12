package com.example.startup.labs



import java.util.*


fun main()= with(Scanner(System.`in`)){
    class Element(myCountry:String,myCity:String,myAmountOfPeople:Int) {
        val country = myCountry //назва країни
        val city = myCity //місто країни
        val amountOfPeople = myAmountOfPeople //кількість людей в країні
        var index = 0 //індекс елементу в массиві
        var nextElement: Element? = null //посилання на насупний елемент
    }

    class Deque
    {
        private var head: Node? = null
        private var tail: Node? = null
        inner class Node(
            var i: Int
        ) {
            var next: Node? = null
            var prev: Node? = null
            fun displayData() {
                print("$i ")
            }
        }
        val isEmpty: Boolean
            get() = head == null
        fun insertFirst(i: Int) {
            val newNode = Node(i)
            if (isEmpty) {
                tail = newNode
            } else {
                head!!.prev = newNode
            }
            newNode.next = head
            head = newNode
        }
        fun insertLast(i: Int) {
            val newNode = Node(i)
            if (isEmpty) {
                head = newNode
            } else {
                tail!!.next = newNode
                newNode.prev = tail
            }
            tail = newNode
        }

        fun removeFirst(): Node {
            if (head == null) {
                throw RuntimeException("Deque is empty")
            }
            val first: Node = head!!
            if (head!!.next == null) {
                tail = null
            } else {
                head!!.next!!.prev = null
            }
            head = head!!.next
            return first
        }

        fun removeLast(): Node {
            if (tail == null) {
                throw RuntimeException("Deque is empty")
            }
            val last: Node = tail!!
            if (head!!.next == null) {
                head = null
            } else {
                tail!!.prev!!.next = null
            }
            tail = tail!!.prev
            return last
        }

        val first: Int
            get() {
                if (isEmpty) {
                    throw RuntimeException("Deque is empty")
                }
                return head!!.i
            }

        val last: Int
            get() {
                if (isEmpty) {
                    throw RuntimeException("Deque is empty")
                }
                return tail!!.i
            }

        fun displayForward() {
            var current = head
            while (current != null) {
                current.displayData()
                current = current.next
            }
            println(" ")
        }
        fun displayBackward() {
            var current = tail
            while (current != null) {
                current!!.displayData()
                current = current!!.prev
            }
            println(" ")
        }
    }



    class Queue(
        private val maxSize: Int
    ) {
        private val queue: IntArray
        var size
                : Int
            private set
        private var front: Int
        private var rear: Int
        fun insert(elem: Int) {
            if (rear == maxSize - 1) {
                rear = -1
            }
            queue[++rear] = elem
            size++
        }

        fun remove(): Int {
            val temp = queue[front++]
            if (front == maxSize) {
                front = 0
            }
            size--
            return temp
        }

        fun getFront(): Int {
            return queue[front]
        }

        fun getRear(): Int {
            return queue[rear]
        }
        init {
            queue = IntArray(maxSize)
            rear = -1
            front = 0
            size = 0
        }
    }

    class Stack(private val maxSize: Int) {
        private val stackArray: IntArray = IntArray(maxSize)
        private var top: Int = -1
        fun addElementToStack(element: Int) {
            stackArray[++top] = element
        }

        fun deleteElementFromStack(): Int {
            return stackArray[top--]
        }

        fun readTop(): Int {
            return stackArray[top]
        }
    }
    class List {
        private var first: Element? = null//посилання на перший елемент
        private var last:  Element? = null//посилання на останній елемент

        val size: Int
            get() {
                var count = 0//присвоюємо розміпу число 0
                val tmp = first//присвоюємо посилання на перший елемент новій змінній
                if (first != null) {
                    count++
                    while (first!!.nextElement != null) {
                        count++//за кожен елемент, що не = 0, збільшуємо розмір на 1
                        first = first!!.nextElement
                    }
                    first = tmp//повертаємо посилання на перший елемент
                } else {
                    return 0
                }
                return count// повертаємо розмір
            }
        fun add(myCountry:String, myCity:String, myAmountOfPeople:Int) {
            val element = Element(myCountry,myCity,myAmountOfPeople)//створення нового елемента
            element.index = size+1//присвоєння індекса новому елементу
            if (first == null) {//якщо першого елемента не існує
                first = element//присвоюєм першому і останньому цей елемент
                last = element
            } else {//якщо ж перший елемент існує
                last!!.nextElement = element//присвоюєм посиланню на наступний елемент посилання на наш новий елемент
                last = element//присваюєм останньому елементу значення нового елемента
            }
        }
        fun remove(index: Int) {
            if (first == null) {//якщо першого елемента не існує
                println("Список пуст")
                return
            }
            if (first === last) {//якщо перший елемент = останньому
                first = null // робимо їх значення null(не вказують ні на що)
                last = null
                return//повернення з функції
            }
            if (first!!.index == index) {//якщо індекс першого елемента = шуканому індексу
                first = first!!.nextElement//переприсвоюєм значення першого елемента, на елемент вперед
                return
            }
            var element = first
            while (element!!.nextElement != null) {//поки наступний елемент існує
                if (element.nextElement!!.index == index) {//якщо індекс наступного елемента = шуканому індексу
                    if (last == element.nextElement) { //якщо шуканий елемент є предостаннім
                        last = element// переприсвоюємо ссилку на останній елемент
                        last!!.index--//зменшуємо його індекс на 1
                    }
                    element.nextElement = element.nextElement!!.nextElement
                    if(element.nextElement != null){//поки наступний елемент існує
                        element = element.nextElement!!//присвоюєм посиланню на елемент посилання на наступний елемент
                        while (element != null) {
                            if (element.index > index) {
                                element.index--//зменшуємо індекс кожного елемента, що йде після видаленого на 1
                            }
                            element = element.nextElement//присвоюєм посиланню на елемент посилання на наступний елемент
                        }
                    }
                    return
                }
                element = element.nextElement
            }
        }
        fun divideList(){
            println("Enter country to sort: ")
            var country = next()//введення назви країни
            println("Enter amount of people to sort: ")
            var amount = nextInt()//введення кількості людей
            var element = first//присвоюємо посилання на перший елемент змінній під назвою елемент
            println("Countries, with amount of people more than $amount: ")
            while (element != null) {//поки елемент існує
                if(element!!.country == country && element!!.amountOfPeople > amount){//якщо кількість людей > записаного числа
                    //виводимо інформацію про даний елемент
                    println("Country: ${element!!.country}, city: ${element!!.city}, amount of people:  ${element!!.amountOfPeople}")
                }
                element = element!!.nextElement//присвоюєм посиланню на елемент посилання на наступний елемент
            }
            element = first//присвоюємо посилання на перший елемент змінній під назвою елемент
            println("Countries, with amount of people less and = $amount: ")
            while (element != null) {//поки елемент існує
                if(element!!.country == country && element!!.amountOfPeople <= amount){//якщо кількість людей <= записаного числа
                    //виводимо інформацію про даний елемент
                    println("Country: ${element!!.country}, city: ${element!!.city}, amount of people:  ${element!!.amountOfPeople}")
                }
                element = element!!.nextElement//присвоюєм посиланню на елемент посилання на наступний елемент
            }
        }
        fun calculateAmount(){
            println("Enter name of the country: ")
             var allAmount = 0
             val country = next()//введення назви країни
             var element = first//присвоюємо посилання на перший елемент змінній під назвою елемент
             while (element != null) {//поки елемент існує
                 if(element!!.country == country){//якщо назва країни дорівнює шуканій назві
                     allAmount += element!!.amountOfPeople//додаємо кількість жителів даного міста до суми
                 }
                element = element!!.nextElement//присвоюєм посиланню на елемент посилання на наступний елемент
            }
            //виводимо інформацію про загальну кількість людей
            println("Amount of people in this country : $allAmount")
        }
        fun printContainer() {
            var element = first//присвоюємо посилання на перший елемент змінній під назвою елемент
            while (element != null) {//поки елемент існує
                //виводимо інформацію про даний елемент
                println("Country: ${element!!.country}, city: ${element!!.city}, amount of people:  ${element!!.amountOfPeople}")
                element = element!!.nextElement//присвоюєм посиланню на елемент посилання на наступний елемент
            }
            println("")
        }
    }
    val list = List()
    while (true) {
        print(
            "Enter command(1 to add element to list,\n\t" +
                    "2 to delete element from list,\n\t" +
                    "3 to divide list,\n\t" +
                    "4 to calculate amount of people in country,\n\t" +
                    "5 to print list\n\t" +
                    "6 to finally close program) \n")
        when(next()){
            "1" -> {
                println("Enter country:")
                var country = next()
                println("Enter city: ")
                var city = next()
                println("Enter amount of people: ")
                var amountOfPeople = nextInt()
                list.add(country,city,amountOfPeople)//добавляємо даний елемент в ліст
            }
            "2" -> {
                println("What element do you want remove?")
                var index = nextInt()
                list.remove(index)//видаляємо елемент по індексу
            }
            "3" -> {
                list.divideList()//викликаємо функцію для розділення ліста за кількістю жителів
            }
            "4" -> {
                list.calculateAmount()//викликаємо функцію для обчислення кількості жителів
            }
            "5" -> {
                list.printContainer()//викликаємо функцію для роздрукування всых елементів
            }
            "6" -> {
                return@with
            }
            else -> {
                print("WRONG INPUT!!!\n")
            }
        }
    }
}