# Material For This Class
* Java
* Basic OOP
* *Data structures (trees, symbol tables, hashes, strings)*
  * implementation of basic operations for each
* iterative & recursive solutions
  * esp. for trees / graphs
* testing / debugging
* programming correctness, efficiency ("performance hit")
* problem solving
* email: david.zaretsky@depaul.edu, office hours 5:00-5:45 CDM Center 224
## Books
* Algorithms 4e
* Core Java SE 9 for the Impatient, 2nd Edition
* Java: How to Program, 11th Edition
# Lecture Plans
  1. Symbol Tables & Binary Search Trees
  2. Balanced Search Trees
  3. Hash Tables
  4. Undirected Graphs
  5. Directed Graphs
  6. String Sort
  7. Regex
  8. Data Compression
# Symbol Tables
* like a hashmap or map structure (in C++)
* key-value pairs
  * ex. 
    * DNS lookup (url-ip)
    * file-system
  * operations
    * inserting a key
    * duplicate entries
      * 1-1 or 1-many or many-1 etc?
* API (363)
  * put(key, value) | get(key) | delete(key) | contains (=> hasProperty) | isEmpty | size | keys
    * is there something to check whether a VALUE is contained in the symbol table (not nec hash-map -- seems like hash map would be a WAY of implementing)?
      * lazy delete -- put NULL in that key -- (memory is still used for that key -- you want to remove the pointer)
* use generics for keys, comparable, .equals
  * .equals -- we don't want to know if two things have the same memory location but if they are the same of their kind
* use immutables for keys (best practice)
  * why is it best to use a an immutable? -- because the key should not change after it has been inserted (hashcode relation is damaged) -- doesn't nec. mean that you can't say add 2 to one of the keys and get another key (in theory)
* hashCode saves memory (instead of putting a string in key)
  * how then is a symbol table different from a hash table?
  * hash also makes complicated mappings possible (object to object, say) [would there be such a mapping?]
* equality test -- use .equals -- symmetric, transitive, and reflexive relation, + non-null
  * null.equals(null)?
  * x == y returns false unless two objects have the same location (computer science version of Frege's problem)
  * == works for short, char, int, byte, Boolean -- but not float and double (rounding? -- what is stored is complicated) (but I'd guess that "==" has built in to use a different method for doubles and floats? -- prof says round or floor to get cleaner values)
  * inherited by all objects
  * be careful about using type in equals signature -- if you use type, comparison defaults to memory test and gives you false (because of overload)
* final (const) -- with classes, a final class cannot be extended
* statement: "If you extend a concrete class and add a new field which contributes to equals, it is not possible to write a perfectly correct equals method for that class." ???
* comparing equality recursively -- classes with sub-classes
* recipe:
  * reference equality => null => type and cast (??? what is the distinction) => fields
* consistency with compareTo (compareTo should return 0 for equal objects and vice versa)
* using a symbol table to sort -- keys are returned in alphabetical order
* frequency test (in book -- for words with at least N letters)
  * go through StdIn -- check that the word is long enough, then add or count based on whether it is contained in the table.
  * use the greatest method to find the max word
* mapping keys to many values <=== look into this
  * unordered linked list attached to that key
  * when adding, look to see if the value is already there -- if not, add it.
    * this gives us a worst case N time
  * average case is N/2 for search and N for insert
  * data is not ordered
  * we would like to do better than linear