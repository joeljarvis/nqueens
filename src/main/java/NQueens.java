import java.util.Arrays;
import java.util.ArrayList;

// This is a mostly obvious recursive solution.
// An improvement would be to use a Sieve of Erasthones approach to adding the
// next row's column (based on previous row's columns).  I think.
// I keep banging my head against the thought of caching bad subtrees in the recursion -
// but they may be dependent upon the nodes closer to the root of the tree (thus, what exactly
// is the relevant sub tree to cache and avoid re-computing?).


// A separate idea is to compute all the boards for n.
// I think an approach on that is to collect each successful board that hits the recursion
// stop condition; but return false, essentially, in order to try additional permutations

// Speaking of permutations, it might be simpler to generate the permutations of the baords
// and check if they are OK.  Generating permutations may be more straightforwrad than this
// recursive algorithm.
// Upon an attempt at doing so (and including all results), computing nqueens for 20 is taking
// too long; much longer than the recursive solution.
// But it's late.  It would be interesting to see if the time is going into the
// positionOK function (which is not designed specifically for the permutation approach).


public class NQueens {

    // The board is a simple array of length n.
    // Each position represents a row, from 0 to n-1.
    // The value in the row is the column (0 to n-1).
    // -1 is the empty/sentinal value.  A board starts with all -1s
    // (because I am using zero-based values)
    int[] board;
    int n;
    ArrayList boards = new ArrayList();

    public NQueens(int n) {
	this.n = n;
	this.board = new int[n];
	Arrays.fill(this.board, -1);
    }

    // This is the main recursive function.
    public boolean placeRow(int r) {
	if (r == this.n) {
	    return(true);
	}
	for (int c = 0; c < this.n; c++) {
	    if (placeColumn(r, c)) {
		return(true);
	    }
	}
	return(false);
    }

    private boolean placeColumn(int r, int c) {
	this.board[r] = c;
	if (positionOK(r, c, this.board) && placeRow(r+1)) {
	    return(true);
	}
	this.board[r] = -1;
	return(false);
    }

    // If the slope of the new position and any previous position
    // is 1 or -1, then it is on the main diagonal.
    // If the column is the same as the column of a previous row, then bad!
    // If there is more than one previous position with the same slope as this new
    // position, then we have more than two queens on the same LINE.
    private boolean positionOK(int r, int c, int[] brd) {
	ArrayList<Float> slopes = new ArrayList<Float>(this.n);
	for (int x = 0; x < r; x++) {
	    int y = brd[x];
	    if (y == c) {
		return(false);
	    }
	    float slope = (float)(y - c) / (float)(x - r);
	    if (Math.abs(slope) == 1) {
		return(false);
	    }
	    if (slopes.contains(slope)) {
		return(false);
	    }
	    slopes.add(slope);
	}
	return(true);
    }

    // shamelessly stolen and modified for my purpose
    // https://www.geeksforgeeks.org/heaps-algorithm-for-generating-permutations/
    private void heapPermutation(int[] a, int size, int n) {
	if (size == 1) {
	    // check the board
	    // System.out.println("checking board: " + Arrays.toString(a));
	    boolean boardOK = true;
	    for (int i=1; i<a.length; i++) {
		if (! positionOK(i, a[i], a)) {
		    boardOK = false;
		    break;
		}
	    }
	    if (boardOK) {
		this.boards.add(a.clone());
		// System.out.println(Arrays.toString(a));
	    }
	}
	for (int i=0; i<size; i++) {
            heapPermutation(a, size-1, n);
 
            // if size is odd, swap first and last
            // element
            if (size % 2 == 1)
            {
                int temp = a[0];
                a[0] = a[size-1];
                a[size-1] = temp;
            }
 
            // If size is even, swap ith and last
            // element
            else
            {
                int temp = a[i];
                a[i] = a[size-1];
                a[size-1] = temp;
            }
        }	
    }


    public static void main(String[] args) {
	int n = Integer.parseInt(args[0]);
	NQueens nqueens = new NQueens(n);
	/* Experimental - is actually slower!
	int[] a = new int[n];
	for (int i=0; i<n; i++) {
	    a[i] = i;
	}
	nqueens.heapPermutation(a, n, n);
	if (nqueens.boards.size() == 0) {
	    System.out.println("No Solutions Found");
	} else {
	    for (int i=0; i<nqueens.boards.size(); i++) {
		int[] brd = (int[])nqueens.boards.get(i);
		System.out.println(Arrays.toString(brd));
	    }
	}
	*/
	nqueens.placeRow(0);
	System.out.println(Arrays.toString(nqueens.board));
    }

}
