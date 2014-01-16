import groovy.util.GroovyTestCase

class ExerciseOneTest extends GroovyTestCase {
	def exerciseOne

	void setUp() {
		exerciseOne = new ExerciseOne()
	}

	void testCalculate() {
		println ""
		pleaseTestThis("2523.04", "Two thousand five hundred twenty-three and 04/100")
		pleaseTestThis(".123", "Zero and 12/100")
		pleaseTestThis("1", "One and 00/100")
		pleaseTestThis("0.1231", "Zero and 12/100")
		pleaseTestThis("123", "One hundred twenty-three and 00/100")
		pleaseTestThis("12000.34", "Twelve thousand and 34/100")
		pleaseTestThis("12010.34", "Twelve thousand ten and 34/100")
		pleaseTestThis("1012010.34", "One million twelve thousand ten and 34/100")
		pleaseTestThis("123456789.29", "One hundred twenty-three million four hundred fifty-six thousand seven hundred eighty-nine and 29/100")
		pleaseTestThis("1000000000.00", "One billion and 00/100")
		pleaseTestThis("10000000.00", "Ten million and 00/100")
		pleaseTestThis("1000000.00", "One million and 00/100")
		pleaseTestThis("1000.00", "One thousand and 00/100")
		pleaseTestThis("100.00", "One hundred and 00/100")
		pleaseTestThis("9999001.00", "Nine million nine hundred ninety-nine thousand one and 00/100")
		pleaseTestThis("748000", "Seven hundred forty-eight thousand and 00/100")
		pleaseTestThis("123123.67", "One hundred twenty-three thousand one hundred twenty-three and 67/100")
		pleaseTestThis("1000000001.00", "One billion one and 00/100")
		pleaseTestThis("1111111111.00", "One billion one hundred eleven million one hundred eleven thousand one hundred eleven and 00/100")
		pleaseTestThis("21212121.00", "Twenty-one million two hundred twelve thousand one hundred twenty-one and 00/100")
		pleaseTestThis("1210000.00", "One million two hundred ten thousand and 00/100")
	}

	void pleaseTestThis(amount, expected) {
		expected = expected + " dollars"
		println "$amount -> $expected"
		assert (exerciseOne.calculate(amount) == expected)
	}

}
