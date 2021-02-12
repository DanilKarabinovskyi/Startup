package com.example.startup.labs

import java.util.*

class Tree{
    class Student(
        var year: Int,
        var mark: Int? = null,
        var name: String? = null)
    class Node(var value: Student) {
        var left: Node? = null
        var right: Node? = null
    }

    var list = mutableListOf<Student>()
    var root: Node? = null

    private fun addRecursive(current: Node?, value: Student): Node? {
        if (current == null) {
            return Node(value)
        }
        if (value.name!! < current.value.name.toString()) {
            current.left = addRecursive(current.left, value)
        } else if (value.name!! > current.value.name.toString()) {
            current.right = addRecursive(current.right, value)
        } else {
            return current
        }
        return current
    }
    private fun addToMarkRecursive(current: Node?, value: Student): Node? {
        if (current == null) {
            return Node(value)
        }
        if (value.mark!! < current.value.mark!!) {
            current.left = addToMarkRecursive(current.left, value)
        } else if (value.mark!! > current.value.mark!!) {
            current.right = addToMarkRecursive(current.right, value)
        } else {
            return current
        }
        return current
    }
    fun add(value: Student) {
        root = addRecursive(root, value)
        list.add(value)
    }
    fun addMark(value: Student) {
        root = addToMarkRecursive(root, value)
    }


    fun deleteRecursive(current: Node?, value: Student): Node? {
        for(i in 0 until list.size){
            if(list[i].name == value.name && list[i].mark == value.mark  && list[i].year == value.year){
                list.removeAt(i)
            }
        }
        if (current == null) {
            return null
        }
        if (value.name == current.value.name) {
            if (current.left == null && current.right == null) {
                return null
            }
            // Case 2: only 1 child
            if (current.right == null) {
                return current.left
            }
            if (current.left == null) {
                return current.right
            }
            // Case 3: 2 children
            val smallestValue: Student = findSmallestValue(current.right)
            current.value = smallestValue
            current.right = deleteRecursive(current.right, smallestValue)
            return current
        }
        if (value.name!! < current.value.year.toString()) {
            current.left = deleteRecursive(current.left, value)
            return current
        }
        current.right = deleteRecursive(current.right, value)
        return current
    }

    private fun findSmallestValue(root: Node?): Student {
        if (root!!.left == null)
            return root.value
        else return findSmallestValue(root.left)
    }

    private fun visit(value: Student) {
        println(" Name: ${value.name}   Mark: ${value.mark}  Year: ${value.year}")
    }

    fun traversePostOrderWithoutRecursion() {
        val stack = Stack<Node?>()
        var prev = root
        var current = root
        stack.push(root)
        while (!stack.isEmpty()) {
            current = stack.peek()
            if(current == null){
                println("Tree is empty")
                return
            }
            val hasChild = current.left != null || current.right != null
            val isPrevLastChild =
                prev === current.right || prev === current.left && current.right == null

            if (!hasChild || isPrevLastChild) {
                current = stack.pop()
                println("Element is last :")
                visit(current!!.value)
                prev = current
            } else {
                if (hasChild || !isPrevLastChild) {
                    println("Element is :")
                    visit(current!!.value)
                }
                if (current.right != null) {
                    stack.push(current.right)
                    println("Child right")
                    visit(current.right!!.value)
                }
                if (current.left != null) {
                    stack.push(current.left)
                    println("Child left")
                    visit(current.left!!.value)
                }
            }
        }
    }
    fun deleteTree(){
        root!!.left = null
        root!!.right = null
        root = null
    }

}
fun main()= with(Scanner(System.`in`)){
    var tree = Tree()
    tree.add(Tree.Student(2006, 91, "BR"))
    tree.add(Tree.Student(2005, 90, "Ane"))
    tree.add(Tree.Student(2004, 92, "Cdin"))
    tree.add(Tree.Student(2003, 97, "Dan"))
    tree.add(Tree.Student(2000, 1, "Lesha"))
    tree.add(Tree.Student(1999, 3, "KeshaNew"))
    tree.traversePostOrderWithoutRecursion()
    tree.deleteRecursive(tree.root,Tree.Student(1999, 3, "KeshaNew"))
    var sum = 0
    tree.deleteTree()
    println("delete")
    for (i in 0 until tree.list.size){
        tree.addMark(tree.list[i])
    }
    tree.traversePostOrderWithoutRecursion()
    for( i in 0 until tree.list.size){
        sum += tree.list[i].year
    }
    println("Ser znach rokiv: ${sum / tree.list.size}")
    println("delete")
    tree.deleteTree()
    tree.traversePostOrderWithoutRecursion()

}
