import org.codehaus.groovy.runtime.StringGroovyMethods

/**
 * global static variables
 */
class globals {
	static ones          = [ '0': "", '1':"one", '2':"two", '3':"three", '4':"four", '5':"five", 
	                         '6':"six", '7':"seven", '8':"eight", '9':"nine" ]
	static tens          = [ "10":"ten", "11":"eleven", "12":"twelve", "13":"thirteen", "14":"fourteen", "15":"fifteen", 
	                         "16":"sixteen", "17":"seventeen", "18":"eighteen", "19":"nineteen" ]
	static greatertens   = [ "2":"twenty", "3":"thirty", "4":"forty", "5":"fifty", 
	                         "6":"sixty", "7":"seventy", "8":"eighty", "9":"ninety" ]
	static denominations = [ "", "ten", "hundred", "thousand", "ten", "hundred", 
	                         "million", "ten", "hundred", "billion", "ten", "hundred", 
	                         "trillion", "ten", "hundred" , "quadrillion", "ten", "hundred", "quintillion"]
}

/**
 * Iterates the amount from left to right spitting out the appropriate number and denomination column.
 *
 */
def calculate(amount) {
	// init
	result = ""
	tenFlag = false
	greaterTenFlag = false
	hundredFlag = false
	tenFirstDigit = ""

	// split amount at the period
	tokens = amount.split("\\.")

	// just take cents and no more
	cents = "00"
	if ( tokens.length == 2 ) {
		cents = tokens[1]
		if (cents.length() == 1) {
			cents = cents + "0"
		} else {
			cents = cents.substring(0,2)
		}
	}

	// reverse amount so it can match the denomination map
	number = StringGroovyMethods.reverse(tokens[0])

	// traverse number from left to right
	topIndex = number.length() - 1
	for ( i in topIndex..0 ) {
		// don't allow to start negative
		if (topIndex < 0) {
			break;
		}

		// digit string
		digit = String.valueOf(number.charAt(i))

		// denomination: ones, tens, hundreds, million, etc
		denomination = globals.denominations[i]

		// merge previous ten denomination with the current number, e.g. 1 and 2 => 12
		if (tenFlag) {
			tenNumber = tenFirstDigit + digit
			result = result + " " + globals.tens[tenNumber] + " " + denomination + " "
			tenFlag = false
			hundredFlag = false
		// other
		} else {
			// ten denomination 
			if ( denomination == "ten" ) {
				// digit 1 is saved and flagged for next iteration
				if ( digit == "1" ) {
					tenFlag = true
					tenFirstDigit = digit

				// ignore 0 digits and process greater tens
				} else if ( digit != "0" ) {
					result = result + " " + globals.greatertens[digit]
					greaterTenFlag = true
				}
			// no ten denomination
			} else {
				// finish hiphenated number by adding the current non-zero digit, e.g. twenty-two
				if ( greaterTenFlag && digit != "0" ) {
					result = result + "-" + globals.ones[digit] + " " + denomination + " "
					greaterTenFlag = false
					hundredFlag = false

				// the most complex expression ever but it works! Note: these were in separate
				//   'if' expressions but I combined them
				// if previously found is a greater ten, then don't add the hyphen
				//   or if digit is not zero, just spit out that number and denomination
				// or
				// if the denomination is other than "ones" and the hundred denomination has
				//   been flagged earlier and digit is zero then we need to spit out the denomination
				//   without the digit (the digit is not displayed because its mapping is empty)
				} else if ( (greaterTenFlag || digit != "0") || 
				            (denomination != "" && hundredFlag && digit == "0") ) {
					result = result + " " + globals.ones[digit] + " " + denomination + " "
					greaterTenFlag = false
					hundredFlag = false
				} // else: any other zero digit is skipped 

				// reset hundred flag when we encounter the hundred denomination and digit is not zero
				if ( denomination == "hundred" && digit != "0" ) {
				    hundredFlag = true
				} 
			}
		}
	}
	
	// special case: eg. .123
	if (result == "") {
		result = "zero"
	}

	// cleanup
	result =  result.trim() + " and $cents/100 dollars"
	result =  result.replaceAll("[ ]+", " ")

	// capitalize first letter and return
	return result.substring(0, 1).toUpperCase() + result.substring(1);
}

//
// THIS IS WHERE EVERYTHING STARTS!
//

// welcome message
println "\nHello! Type an amount and you'll see!"
println "Type 'quit' to finish"

// prompt user
br = new BufferedReader(new InputStreamReader(System.in))
while (true) {
	print "\nAmount: "

	// read input
	def amount = br.readLine()

	// do some validation
	if ( !amount.isNumber() ) {
		// quit
		if (amount == "quit") {
			println "bye"
			break;
		// not a number
		} else {
			println "not a number.  try again"
		}
	// almost there
	} else {
		// negative amounts are not fair
		if (amount.toFloat() < 0) {
			println "please enter a non-negative amount.  try again"
		// DO magic
		} else {
			println "Answer: " + calculate(amount)
		}
	}
}
