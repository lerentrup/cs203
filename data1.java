

public interface FiniteSet {
    public int cardinality();
    public boolean isEmptyHuh();
    public boolean member(int x);
    public FiniteSet add(int x);
    public FiniteSet remove(int x);
    public FiniteSet union(FiniteSet u);
    public FiniteSet inter(FiniteSet u);
    public FiniteSet diff(FiniteSet u);
    public boolean equal(FiniteSet u);
    public boolean subset(FiniteSet u);
    public String toString();
}

 class EmptySet implements FiniteSet {
    
     EmptySet(){}
     
     // Since empty sets are always empty, returns 0 for the cardinality...
     public int cardinality(){
         return 0;
     }
     
     // Since empty sets are alway empty, should always return true...
     public boolean isEmptyHuh(){
         return true;
     }
     
     // Since empty sets are always empty, should always return false,
     // otherwise it would be a finite set...
     public boolean member(int x){
         return false;
     }
     
     // Adding an integer to an empty set makes it a finite set, so this 
     // calls the constructor for NonEmptySet...
     public FiniteSet add(int x){
         return new NonEmptySet(x, this, this);
     }

    // Remove something that isn't there/makes this an empty set, so return an empty set
    public FiniteSet remove(int x) {
            return this;
    }

    // Call this function on an empty set, and takes in another FiniteSet called u- this should just
    // return the set "u," since the union of any set A and the empty set is set A...
    public FiniteSet union(FiniteSet u) {
        return u;
    }

    // Call this function on an empty set, and takes in another FiniteSet called u- this shold return 
    // the empty set (this, since the function is being called on an empty set), as the intersection of 
    // an empty set with any other set (including another empty set) is the empty set...
    public FiniteSet inter(FiniteSet u) {
        return this;
    }

    // Again, call this function on an empty set, and takes in another FiniteSet called u- this should just
    // return the set "u," since removing the empty set from any set A should just result in set A...
    public FiniteSet diff(FiniteSet u) {
        return u;
    }

    // Again, call this function on an empty set, and takes in another FiniteSet called u- this should always 
    // return true, since the empty set is a subset of every set (or is this supposed to be checking for a proper subset??)
    public boolean subset(FiniteSet u) {
        return true;
        // if it was checking for a proper subset, would look something like this:
        // if(u.cardinality() == 0)
        //  return false;
        // else
        //  return true;
        // or should this be a separate function... properSubset?
    }
    
    // If two sets are equal, they should both be subsets of one another, so this method uses the subset call twice
    // Call this function on an empty set, and takes in another FiniteSet called u- it then checks if u is a subset
    // of the set being called, an if the set being called is a subset of u...
    public boolean equal(FiniteSet u) {
        if(this.subset(u) == true && u.subset(this) == true);
            return false;
                else
                return true
            
    }
    // ToString method just for testing- just returns an empty string to represent the empty set
    public String toString() {
        return "";
    }
    
    // class representing the non empty set...
    public class NonEmptySet implements FiniteSet {
    // integer marking the location of the present integer (instead of "key")
    int loc;
    FiniteSet toLeft;
    FiniteSet toRight;
    
    // Constructor that initializes the global variables loc, toLeft and toRight, and sets them
     NonEmptySet(int loc, FiniteSet toLeft, FiniteSet toRight){
        this.loc = loc;
        this.toLeft = toLeft;
        this.toRight = toRight;
    }
    
   // The "base cardinality" is 1 (if it were less, i.e. 0, it would be an empty set), which is added onto the 
   // recursive call of the function onto the left of the function (which should run until cardinality = 0) and
   // then onto the recursive call onto the right side.
   public int cardinality(){
       return 1 + toLeft.cardinality() + toRight.cardinality();
   }
    
   // A finite set is never empty, so the isEmptyHuh call always returns false 
   public boolean isEmptyHuh(){
       return false;       
   }
   
   // Recursively walks through the set, checking if "loc" at every position is equal to x- if it is,
   // the function stops and returns true (otherwise it keeps checking- if x is greater than loc, it checks 
   // to the right, if x is less than loc, it checks to the right, since the set, holding the 
   // properties of a binary search tree *should* be in order)
   public boolean member(int x){
       if(x == this.loc)
           return true;
       else if(x < this.loc)
           return toLeft.member(x);
       else if(x > this.loc)
           return toRight.member(x);
       else
           return false;
    }
   
   // Called onto a non empty set, with integer x as input... first checks is x is a member of the set on 
   // which the function is being called which case the function only returns the set on which it is being
   // called (since sets do not contain repeats). 
   public FiniteSet add(int x){
       if(this.member(x))
           return this;
        // if x is less than the value of loc...
       else if (x < this.loc)
       // run the function again, with loc, the add function called onto the left set and the right set...
           return new NonEmptySet(loc, toLeft.add(x), toRight);
       // if x is greater than loc...
       else if (x > this.loc)
       // run the function again, with loc, the left set and the add function called onto the right set...
           return new NonEmptySet(loc, toLeft, toRight.add(x));  
       // finally, if the program has arrived add the right location for x...
       else
       // it returns a new set containing x, everything to the left and everything to the right...
           return new NonEmptySet(x, toLeft, toRight);
    }
   
   // recursively checks the set until x has been found, and then returns 
   // the union of whatever is to the left and to the right of x...
    public FiniteSet remove(int x) {
        if(x == this.loc)
            return toLeft.union(toRight);
        else if(x > this.loc)
            return new NonEmptySet(loc, toLeft, toRight.remove(x))
        else /* if x is less than this.loc */
            return new NonEmptySet(loc, toLeft.remove(x), toRight;
    }

   // Recursively called on the left part of the set it's being called, with the right side as input. It recursively 
   // walks through the set it's being called on, essentially creating a new set each time- but, more importantly,
   // it gives a new value for loc with each recursion. Union is called on this "changing set," with u as an input,
   // which recusievly walks through the whole set- this time adding the value of loc from the previous set.
    public FiniteSet union(FiniteSet u) {
        return toLeft.union(toRight).union(u).add(loc);
    }
    
   // not sure yet
    public FiniteSet inter(FiniteSet u) {
 
    }

  // not sure yet
    public FiniteSet diff(FiniteSet u) {
       
    }

    // Called on a non empty set, with another finite set ("u") as an input. It checks if loc (from the set that the 
    // function is being called on) is a member of u, then recursively checks the sets to the left and right of loc
    // with the same subset function call (as soon as u.member(this.loc) returns false, the function should return false
    // as well)
    public boolean subset(FiniteSet u) {
        if(u.member(this.loc) && toLeft.subset(u) && toRight.subset(u))
            return true;
        else
            return false;
    }
   
    // Called on a non empty set, with another finite set ("u") as an input. It works in the same way as it did
    // for the empty set- it checks if the set it's being called on is a subset of u, and if u is a subset of the 
    // the set the original function is being called on...
    public boolean equal(FiniteSet u) {
        if(this.subset(u) == true && u.subset(this) == true)
            return true;
        else
            return false;
    }

   
 
   // toString function for testing...
    public String toString() {
        return Integer.toString(this.loc) + " " + this.toLeft + " " + this.toRight;
    }
           
}
  public static void main(String[] args){
        
       FiniteSet testSet0;
       FiniteSet testSet1;
       
       testSet0 = new EmptySet();
       System.out.println(testSet0.cardinality());
       System.out.println(testSet0.isEmptyHuh());
       System.out.println(testSet0.member(1));
       
       testSet1 = new EmptySet(); 
       for(int x = 1; x <= 10; x = x + 1)
           testSet1 = testSet1.add(x);
    
