# SQL Table Definition

from functools import reduce

# Converts a single foreign key into a SQL statement of the form:
# `FOREIGN KEY [atts] REFERENCES [table][atts] 
def fk_to_string(fk):
    (my_atts, foreign_table, foreign_atts) = fk

    return "FOREIGN KEY(`" + \
    "`, `".join(str(a) for a in my_atts) + \
    "`) REFERENCES `" + \
    foreign_table + "`(`" + \
    "`, `".join(str(a) for a in foreign_atts) + \
    "`)"

# Corresponds to a MySQL table that supports primary and foreign keys and only INT attributes.
# It consists of four member variables:
#   * [name] The name of the table (unique identifier/slug)
#   * [attributes] The set of string-valued attributes in this table
#   * [primary_key] The set of string-valued attributes that form the primary key of this table
#   * [foreign_keys] The set of foreign keys. Each foreign key is a three-tuple of:
#       - An ordered tuple of attributes in this table to which the FK is applied
#       - The name of the table that the foreign key should reference
#       - An ordered tuple of attributes in the foreign table that are being referenced 
class Table:
    name = 'T'
    attributes = set()
    primary_key = set()
    foreign_keys = set()
    def __init__(self, name, attributes, primary_key, foreign_keys):
        self.name = name
        self.attributes = attributes
        self.primary_key = primary_key
        self.foreign_keys = foreign_keys
    def __str__(self):
        return "CREATE TABLE `" + \
        self.name + \
        "`(" + \
        (("`" + "` INT, `".join(self.attributes) + "` INT") if len(self.attributes) > 0 else "") + \
        ")" + \
        ((", PRIMARY KEY(`" + "`, `".join(self.primary_key) + "`)") if len(self.primary_key) > 0 else "") + \
        (", " + ", ".join([fk_to_string(fk) for fk in self.foreign_keys]) if len(self.foreign_keys) > 0 else "") + \
        ");" 
    def __repr__(self):
        return str(self)
    def __hash__(self):
        return hash(self.name) ^ reduce(lambda x,y: x^y, [hash(x) for x in self.attributes])
    def __eq__(self, other):
        my_fk_1 = set([(frozenset(x[0]), x[1], frozenset(x[2])) for x in self.foreign_keys])
        my_fk_2 = set([(frozenset(x[0]), x[1], frozenset(x[2])) for x in other.foreign_keys])
        return \
        self.name == other.name and \
        self.attributes == other.attributes and \
        self.primary_key == other.primary_key and \
        my_fk_1 == my_fk_2
        #self.foreign_keys== other.foreign_keys

# Corresponds to an entire MySQL database of tables
# It consists of just one member variable:
#   * An unordered list of the Table objects that make up this database
class Database:
    tables = []
    def __init__(self, tables):
        self.tables = tables
    def __eq__(self, other):
        return set( self.tables ) == set( other.tables )


# An example instantiation of the Database class.
sample_db = Database([ \
			Table('A', set(['a1','a2']), set(['a1']), set()), \
			Table('B', set(['b1','b2']), set(['b1']), set()), \
			Table('R1', set(['a1','b1', 'x']), set(['a1','b1']), \
				set([(('a1',), 'A', ('a1',)), (('b1',), 'B', ('b1',))]))])

sample_db3 = Database([ \
    Table('A', set(['a1','a2','a3']), set(['a1','a2']), set()), \
    Table('B', set(['b1','b2','wb1']), set(['b1','wb1']), set([(('wb1',), 'wB', ('wb1',))])), \
    Table('wB', set(['wb1']), set(['wb1']), set()), \
    Table('R1', set(['a1','b1','a2','wb1','x','y']), set(['a1','b1','a2','wb1','x']), \
        set([(('a1','a2',), 'A', ('a1','a2',)), (('b1','wb1',), 'B', ('b1','wb1',))]))])

T1_solution =  Database([
	Table('A', set(['a1']), set(['a1']), set())
])

T2_solution =  Database([
	Table('A', set(['a1', 'a2']), set(['a1', 'a2']), set())
])


T3_solution = Database([
	Table('A', set(['a1', 'a2']), set(['a1']), set())
])

T4_solution = Database([
	Table('A', set(['a1','a2']), set(['a1']), set()),
	Table('B', set(['b1','b2']), set(['b1']), set()),
	Table('R1', set(['a1','b1']), set(['a1','b1']),
		set([(('a1',), 'A', ('a1',)), (('b1',), 'B', ('b1',))]))
])


T5_solution = Database([
	Table('A', set(['a1','a2']), set(['a1']), set()),
	Table('B', set(['b1','b2']), set(['b1']), set()),
	Table('R1', set(['a1','b1','x']), set(['a1','b1','x']),
		set([(('a1',), 'A', ('a1',)), (('b1',), 'B', ('b1',))]))
])


T6_solution = Database([
	Table('A', set(['a1','a2']), set(['a1']), set()),
	Table('B', set(['b1','b2']), set(['b1']), set()),
	Table('R1', set(['a1','b1','x','y']), set(['a1','b1','x']),
		set([(('a1',), 'A', ('a1',)), (('b1',), 'B', ('b1',))]))
])


T7_solution = Database([
	Table('A', set(['a1','a2','b1']), set(['a1']), 
		set([ (('b1',), 'B', ('b1',)) ])),
	Table('B', set(['b1','b2']), set(['b1']), set())
])


T8_solution = Database([
    Table('A', set(['a1', 'a2', 'b1', 'x']), set(
        ['a1', 'x']), set([(('b1',), 'B', ('b1',))])),
    Table('B', set(['b1', 'b2']), set(['b1']), set())]) 

T9_solution = Database([
    Table('A', set(['a1', 'a2']), set(['a1']), set()),
    Table('B', set(['b1', 'b2', 'a1']), set(['b1', 'a1']), set([(('a1',), 'A', ('a1',))]))])

T10_solution = Database([
    Table('A', set(['a1', 'a2']), set(['a1']), set([])),
    Table('B', set(['b1', 'b2', 'a1']), set(['b1', 'a1']), set([(('a1',), 'A', ('a1',))]))])
# print(sample_db.tables)
