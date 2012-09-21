Home Inventory Tracker
===

[![Build Status](https://secure.travis-ci.org/CS340Group/hit.png)](http://travis-ci.org/CS340Group/hit)

[Functional Spec](http://students.cs.byu.edu/~cs340ta/fall2012/group_project/Inventory-Tracker-Functional-Spec.pdf)

[Reports](http://cs340group.github.com/hit/)

===
# Questions for meeting:
In our structure, I can't figure out how StorageUnit is any different than ProductContainer. Do we need a whole separate class?  
Should we add a method to the Product class that takes a UPC in and searches the internet, filling out the product info?  


===
# Tips and reminders:

* Abstract as much as possible. E.g. Use `Collection` instead of `ArrayList`.
* Use interfaces for models so that multiple implementations can be used.
* It can be okay for a lower layer to call an upper layer, but it's best if the lower layer defines an interface
which the upper layer then implements. This is called **dependency inversion**.

===
# Todo

* Email Bibek about barcodes.