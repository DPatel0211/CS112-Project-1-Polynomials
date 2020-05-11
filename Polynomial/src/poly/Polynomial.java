package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the toReturn of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the toReturn of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		Node foo = poly1;
		Node bar = poly2;
		Node toReturn = null;

		if(foo == null && bar == null) {
			return null;
		}
		//vice-versa of lines prior ^
		else if (bar == null) {
			while (foo != null) {
				if(toReturn == null)
					toReturn = new Node(foo.term.coeff, foo.term.degree, null);
				else {
					Node h = toReturn;
					while(h.next != null)
						h = h.next;
					h.next = new Node(foo.term.coeff, foo.term.degree, null);
					//System.out.println("Huzzah!");
				}
				foo = foo.next;
			}

			return toReturn;
		}
		else if (foo == null) {
			while(bar != null) {
				if(toReturn == null) {
					toReturn = new Node(bar.term.coeff, bar.term.degree, null);
				} else {
					Node t = toReturn;
					while(t.next != null)
						t = t.next;
					t.next = new Node(bar.term.coeff, bar.term.degree, null);
					// System.out.println("Yerr!");
				}
				bar = bar.next;
			}

			return toReturn;
		}
// second set of conditionals to follow below what if the degrees are the same?:

		if(foo.term.degree == bar.term.degree) {
			if(foo.term.coeff + bar.term.coeff != 0)
				toReturn = new Node(foo.term.coeff + bar.term.coeff, foo.term.degree, null);
			foo = foo.next;
			bar = bar.next;
		} else if(foo.term.degree < bar.term.degree) {
			toReturn = new Node(foo.term.coeff, foo.term.degree, null);
			foo = foo.next;
		} else {
			toReturn = new Node(bar.term.coeff, bar.term.degree, null);
			bar = bar.next;
			//System.out.println("Vibe/Case Test Check :)");
		}
		// NOTE: CONTINUE SKIPS CHECKING THE OTHER CONDITIONALS AND ITERATES THE WHILE LOOP!
		while(foo != null && bar != null) {
			if(foo.term.degree == bar.term.degree) {
				if(foo.term.coeff + bar.term.coeff == 0) {
					foo = foo.next;
					bar = bar.next;
					continue;
				}
				if(toReturn == null) {
					toReturn = new Node(foo.term.coeff + bar.term.coeff, foo.term.degree, null);
					foo = foo.next;
					bar = bar.next;
					continue;
				}
				Node next = new Node(foo.term.coeff + bar.term.coeff, foo.term.degree, null);
				Node l = toReturn;
				while(l.next != null) {
					l = l.next;
				}
				l.next = next;
				foo = foo.next;
				bar = bar.next;
				//System.out.println("Another vibe/case test check :)");
			}
			else if(foo.term.degree < bar.term.degree) {
				if(toReturn == null) {
					toReturn = new Node(foo.term.coeff, foo.term.degree, null);
					foo = foo.next;
					continue;
				}
				Node next = new Node(foo.term.coeff, foo.term.degree, null);
				Node r = toReturn;
				while(r.next != null)
					r = r.next;
				r.next = next;
				foo = foo.next;
			} else if(bar.term.degree < foo.term.degree) {
				if(toReturn == null) {
					toReturn = new Node(bar.term.coeff, bar.term.degree, null);
					bar = bar.next;
					continue;
				}
				Node next = new Node(bar.term.coeff, bar.term.degree, null);
				Node i = toReturn;
				while(i.next != null) { // does adding brackets affect this? i forgot to add them when writing this lol
					i = i.next;
				}
				i.next = next;
				bar = bar.next;
			}
		}

		while(foo != null) {
			if(toReturn == null) {
				toReturn = new Node(foo.term.coeff, foo.term.degree, null);
				foo = foo.next;
				continue;
			}
			Node next = new Node(foo.term.coeff, foo.term.degree, null);
			Node c = toReturn;
			while(c.next != null)
				c = c.next;
			c.next = next;
			foo = foo.next;
		}

		while(bar != null) {
			if(toReturn == null) {
				toReturn = new Node(bar.term.coeff, bar.term.degree, null);
				bar = bar.next;
				continue;
			}
			Node next = new Node(bar.term.coeff, bar.term.degree, null);
			Node s = toReturn;
			while(s.next != null) {
				s = s.next;
			}
			s.next = next;
			bar = bar.next;
		}

		// OLD APPROACHES THAT DIDNT WORK
		/*Node temp = new Node(0,0,null);
		while (temp != null) {
			temp.next = new Node(0,0,null);
			if (head.term.coeff == 0) {
				head = head.next;
			}
			temp.term.coeff = head.term.coeff;
			temp.term.degree = head.term.degree;
		}*/

		/*
		Node t = head;
		Node h = t;
		Node prev = null;
		int waitasec = 0;
		//Node h = head;
		//int i = 0;
		int count = 0;
		int zeroes = 0;

		while (t != null) {
			count++;
			if (t.term.coeff == 0) {
				waitasec++;
				zeroes++;
				if (t.next == null) {
					prev.next = null;
					break;
				}
				System.out.println(t.term.coeff + "^" + t.term.degree);
				Node temp = t.next;
				t.term.coeff = temp.term.coeff;
				t.term.degree = temp.term.degree;
				t.next = temp.next;
				temp = null;
			}
			prev = t;
			t = t.next;
		}

		if (count == zeroes) {
			System.out.println("FIRST");
			return null;
		}

		if (waitasec > 0) {
			System.out.println("SECOND");
			return h;
		}
		 */

		return toReturn;
	}
/* Helper method i ended up binning lol
	public static Node deleteZero(Node front) {
		Node temp = front;
		if (front == null) {
			return null;
		}
		while (temp.next != null) {
			if (front.next.term.coeff != 0) {
				temp = temp.next;
			}
			temp.next = temp.next.next;
		}
		return front;
	}
*/
	/**
	 * Returns the toReturn of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the toReturn of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node foo = poly1;
		Node bar = poly2;
		Node toReturn = null;

		// if both inputs are null return null
		if(foo == null || bar == null)
			return null;
		else {
			while (foo != null) {
				bar = poly2;
				// traces back each iteration ^

				// FOIL - CS Style :)
				Node h = new Node((foo.term.coeff * bar.term.coeff), (foo.term.degree + bar.term.degree), null);
				Node toHold = h;
				bar = bar.next;
				while (bar != null) {
					float prodC = (foo.term.coeff * bar.term.coeff);
					int nDeg = foo.term.degree + bar.term.degree;
					Node t = new Node(prodC, nDeg, null);
					h.next = t;
					h = h.next;
					bar = bar.next;
				}

				foo = foo.next;
				// adds the polynomial expresses so that it is cleaner to work with in the next iteration
				toReturn = Polynomial.add(toReturn, toHold);

			}
		}

		return toReturn;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) { // TIP: TRY USING HORNERS METHOD FOR THIS
		float toReturn = 0;
		while (poly != null) {
			toReturn += (poly.term.coeff)*Math.pow(x,poly.term.degree);
			poly = poly.next;
		}
		return toReturn;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
