

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
    // return true, since the empty set is a subset of every set 
    public boolean subset(FiniteSet u) {
        return true;
    }
    
    // If two sets are equal, they should both be subsets of one another, so this method uses the subset call twice
    // Call this function on an empty set, and takes in another FiniteSet called u- it then checks if u is a subset
    // of the set being called, an if the set being called is a subset of u...
    public boolean equal(FiniteSet u) {
        if(this.subset(u) == true && u.subset(this) == true)
            return false;
        else
            return true;
            
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
    
   // Checks if loc is a member of u (in which case it will be a member of the intersection set). If this is true,
   // it creates a new non empty set that recursively calls the constructor with loc, and the recursive calls on 
   // the left and right sides of the set it's being called on (with u as the input). Otherwise it recursively calls 
   // function on the rest of the function it's being called on, with loc removed (and u as input)
    public FiniteSet inter(FiniteSet u) {
        if(u.member(loc)){
            return new NonEmptySet(loc, toLeft.inter(u), toRight.inter(u));
        } else{
            return this.remove(loc).inter(u);
    }

  // Walks the the set (via the union function, applied to the left and right side of the set the function is 
  // being called on), by recursively calling the diff function on this set and removing loc each time (this leaves
  // the set u with every instance of loc- i.e. every element of the set it's being called on- removed)
    public FiniteSet diff(FiniteSet u) {
       return toLeft.union(toRight).diff(u).remove(loc);
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
        return this.toLeft + Integer.toString(this.loc) + " " + this.toRight;
    }
           
}


// seperate class for running tests
public class test{
  public static void main(String[] args){
        
        // method that runs tests on empty sets
      public static void emptySetTests(FiniteSet u){
        
       int rand =(int) (1000*Math.random());
       
       System.out.println("TESTS ON AN EMPTY SET...");
       System.out.println("-----------------------------------------------------------");
       System.out.println("EMPTY SET CARDINALITY: " + u.cardinality() + " (SHOULD BE 0)");
       System.out.println("IS EMPTY SET EMPTY: " + u.isEmptyHuh() + " (SHOULD BE TRUE)");
       System.out.println("CHECK IF RANDOM INTEGER " + rand + " IS MEMBER: " + u.member(rand) + " (SHOULD BE FALSE)");
       System.out.println("ADDING RANDOM INTEGER: " + rand + "...");
       u = u.add(rand);
       System.out.println("NEW SET CARDINALITY: " + u.cardinality() + " (SHOULD BE 1)");
       System.out.println("IS NEW SET EMPTY: " + u.isEmptyHuh() + " (SHOULD BE FALSE)");
       System.out.println("CHECK IF RANDOM INTEGER " + rand + " IS MEMBER: " + u.member(rand) + " (SHOULD BE TRUE)");
       System.out.println("-----------------------------------------------------------");
        
    }
    
    // method to generate a random set of length n
       public static FiniteSet genSet(int n){
       
        FiniteSet testSet2 = new EmptySet();
        
        for(int x = 1; x <= n; x++){
            int rand = (int) (100*Math.random());
                  while(testSet2.member(rand)){
                        rand =(int) (100*Math.random());}
              testSet2 = testSet2.add(rand);}        
        return testSet2;}
       
       // static method that tests two given tests, testSet1 and testSet2
        public static void runPredictedTests(FiniteSet u, FiniteSet v){
           System.out.println("TESTS ON TESTSET1 and TESTSET2...");
           System.out.println("-----------------------------------------------------------");
           System.out.println("SET 1: " + u.toString() + " (LENGTH: " + u.cardinality() + ")");
           System.out.println("SHOULD BE: 1 2 3 4 5 6 7 8 10, LENGTH: 10");
           System.out.println("SET 2: " + v.toString() + " (LENGTH: " + u.cardinality() + ")");
           System.out.println("SHOULD BE: 5 6 7 8 9 10 11 12 13 14 15, LENGTH: 10");
           System.out.println("ARE SETS EQUAL? " + v.equal(u));
           System.out.println("SHOULD BE FALSE");
           System.out.println("UNION: " + u.union(v).toString());
           System.out.println("SHOULD BE: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
           System.out.println("1 TO 2 DIFFERENCE: " + u.diff(v).toString());
           System.out.println("SHOULD BE: 11 12 13 14 15");
           System.out.println("2 TO 1 DIFFERENCE: " + v.diff(u).toString());
           System.out.println("SHOULD BE: 1 2 3 4");
           System.out.println("INTERSECTION: " + u.inter(v).toString());
           System.out.println("SHOULD BE: 5 6 7 8 9 10");
           System.out.println("IS 1 SUBSET OF 2? " + u.subset(v));
           System.out.println("SHOULD BE FALSE");
           System.out.println("IS 2 SUBSET OF 1? " + v.subset(u));
           System.out.println("SHOULD BE FALSE");
           System.out.println("-----------------------------------------------------------");
       }
       
       // static method that tests two sets: the primes from 3 to 17 and the odd numbers from 1 to 19
       public static void runSecondPredictedTests(){
           
           FiniteSet u = new EmptySet();
           u = u.add(3);
           u = u.add(5);
           u = u.add(7);
           u = u.add(11);
           u = u.add(13);
           u = u.add(17);
                    
           FiniteSet v = new EmptySet();
           v = v.add(1);
           v = v.add(2);
           v = v.add(3);
           v = v.add(5);
           v = v.add(7);
           v = v.add(9);
           v = v.add(11);
           v = v.add(13);
           v = v.add(15);
           v = v.add(17);
           v = v.add(19);
         
           System.out.println("TESTS ON PRIME SET and ODD SET...");
           System.out.println("-----------------------------------------------------------");
           System.out.println("SET 1: " + u.toString() + " (LENGTH: " + u.cardinality() + ")");
           System.out.println("SHOULD BE: 3 5 7 11 13 17, LENGTH: 6");
           System.out.println("SET 2: " + v.toString() + " (LENGTH: " + v.cardinality() + ")");
           System.out.println("SHOULD BE: 1 2 3 5 6 7 9 11 13 15 17 19, LENGTH: 11");
           System.out.println("ARE SETS EQUAL? " + v.equal(u));
           System.out.println("SHOULD BE FALSE");
           System.out.println("UNION: " + u.union(v).toString());
           System.out.println("SHOULD BE: 1 2 3 5 6 7 9 11 13 15 17 19");
           System.out.println("1 TO 2 DIFFERENCE: " + u.diff(v).toString());
           System.out.println("SHOULD BE: 1 2 9 15 19");
           System.out.println("2 TO 1 DIFFERENCE: " + v.diff(u).toString());
           System.out.println("SHOULD BE EMPTY");
           System.out.println("INTERSECTION: " + u.inter(v).toString());
           System.out.println("SHOULD BE: 1 3 5 7 11 13 17");
           System.out.println("IS 1 SUBSET OF 2? " + u.subset(v));
           System.out.println("SHOULD BE TRUE");
           System.out.println("IS 2 SUBSET OF 1? " + v.subset(u));
           System.out.println("SHOULD BE FALSE");
           System.out.println("-----------------------------------------------------------");
       } 
       
       // static method that runs tests on two inputted sets
       public static void runTests(FiniteSet u, FiniteSet v){
           System.out.println("TESTS ON TWO INPUTTED SESTS...");
           System.out.println("-----------------------------------------------------------");
           System.out.println("SET 1: " + u.toString() + " (LENGTH: " + u.cardinality() + ")");
           System.out.println("SET 2: " + v.toString() + " (LENGTH: " + u.cardinality() + ")");
           System.out.println("ARE SETS EQUAL? " + v.equal(u));
           System.out.println("UNION: " + u.union(v).toString());
           System.out.println("1 TO 2 DIFFERENCE: " + u.diff(v).toString());
           System.out.println("2 TO 1 DIFFERENCE: " + v.diff(u).toString());
           System.out.println("INTERSECTION: " + u.inter(v).toString());
           System.out.println("IS 1 SUBSET OF 2? " + u.subset(v));
           System.out.println("IS 2 SUBSET OF 1? " + v.subset(u));
           System.out.println("-----------------------------------------------------------");
       }
       
       // static method that tests two random sets, each of length n    
       public static void runTestsOnRandomSets(int n){
       
           FiniteSet u = genSet(n);
           FiniteSet v = genSet(n);
           System.out.println("TESTS ON TWO RANDOM SETS...");
           System.out.println("-----------------------------------------------------------");
           System.out.println("SET 1: " + u.toString());
           System.out.println("SET 2: " + v.toString());
           System.out.println("UNION: " + u.union(v).toString());
           System.out.println("1 TO 2 DIFFERENCE: " + u.diff(v).toString());
           System.out.println("2 TO 1 DIFFERENCE: " + v.diff(u).toString());
           System.out.println("INTERSECTION: " + u.inter(v).toString());
           System.out.println("IS 1 SUBSET OF 2: " + u.subset(v));
           System.out.println("IS 2 SUBSET OF 1: " + v.subset(u));
           System.out.println("-----------------------------------------------------------");
       }
       
          
    public static void main(String[] args){
        
  
       FiniteSet testSet0;
       FiniteSet testSet1;
       FiniteSet testSet2;
   
       
       testSet0 = new EmptySet();
       emptySetTests(testSet0);
       System.out.println();
        
       testSet1 = new EmptySet(); 
       for(int x = 1; x <= 10; x = x + 1)
           testSet1 = testSet1.add(x);
       
         testSet2 = new EmptySet(); 
       for(int x = 5; x <= 15; x = x + 1)
           testSet2 = testSet2.add(x);
       
     
       runPredictedTests(testSet1, testSet2);
       System.out.println();
       
       FiniteSet newTestSet = genSet(15);
       FiniteSet newTestSet1 = genSet(15);
                
       runTests(newTestSet, newTestSet1);
        System.out.println();
       
       runTestsOnRandomSets(5);
        System.out.println();
       
       runTestsOnRandomSets(10);
        System.out.println();
       
       runTestsOnRandomSets(20);
         System.out.println();
    
}
