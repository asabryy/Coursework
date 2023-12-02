from random import sample
from erd import *
from table import *
from erd_converter_recu import convert_to_table

import unittest

# Check that the `__eq__` function works correctly on the sample table
class TestEquality(unittest.TestCase):
	def test_equal_db(self):
		sample_db2 = Database([ \
			Table('A', set(['a1','a2']), set(['a1']), set()), \
			Table('B', set(['b1','b2']), set(['b1']), set()), \
			Table('R1', set(['a1','b1']), set(['a1','b1']), \
				set([(('a1',), 'A', ('a1',)), (('b1',), 'B', ('b1',))]))])
		self.assertEqual( sample_db, sample_db2 )


# Check that the `convert_to_table()` function converts the sample_erd into the sample_db
class TestSample(unittest.TestCase):
	def test_converter(self):
		#print(sample_db3.tables)
		self.assertEqual( sample_db3, convert_to_table( sample_erd2 ) )
	
	def test_converter1(self):
		#print(sample_db3.tables)
		self.assertEqual( T1_solution, convert_to_table( T1 ) )

	def test_converter2(self):
		#print(sample_db3.tables)
		self.assertEqual( T2_solution, convert_to_table( T2 ) )

	def test_converter3(self):
		#print(sample_db3.tables)
		self.assertEqual( T3_solution, convert_to_table( T3 ) )

	def test_converter4(self):
		#print(sample_db3.tables)
		self.assertEqual( T4_solution, convert_to_table( T4 ) )

	def test_converter5(self):
		#print(sample_db3.tables)
		self.assertEqual( T5_solution, convert_to_table( T5 ) )

	def test_converter6(self):
		#print(sample_db3.tables)
		self.assertEqual( T6_solution, convert_to_table( T6 ) )
	
	def test_converter7(self):
		#print(sample_db3.tables)
		self.assertEqual( T7_solution, convert_to_table( T7 ) )
	
	def test_converter8(self):
		#print(sample_db3.tables)
		self.assertEqual( T8_solution, convert_to_table( T8 ) )
	
	def test_converter9(self):
		#print(sample_db3.tables)
		self.assertEqual( T9_solution, convert_to_table( T9 ) )
	
	def test_converter10(self):
		#print(sample_db3.tables)
		self.assertEqual( T10_solution, convert_to_table( T10 ) )
	
	


# Run all unit tests above.
unittest.main(argv=[''],verbosity=2, exit=False)
