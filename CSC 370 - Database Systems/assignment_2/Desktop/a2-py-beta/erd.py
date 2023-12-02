# Entity-Relationship Diagram (ERD) Definition

from enum import Enum

# Corresponds to a *relationship* in an Entity-Relationship Diagram (ERD)
# It consists of three member variables:
#  * [name] a name (unique identifier)
#  * [attributes] an unordered list of attributes that are a property *of the relationship*
#  * [primary_key] an unordered sublist of those attributes that are necessary to fully define the primary key of this relationship
#
# NB: primary_key is a subset of attributes 
class Relationship:
    name = 'R'
    attributes = []
    primary_key = []
    def __init__(self, name, attributes, primary_key):
        self.name = name
        self.attributes = attributes
        self.primary_key = primary_key

# Possible values for the multiplicity of an entity set with respect to a relationship
class Multiplicity(Enum):
    ONE = 0
    MANY = 1

# Corresponds to an *entity set* in an Entity-Relationship Diagram (ERD)
# It consists of six member variables:
#   * [name] a name (unique identifier)
#   * [attributes] an unordered list of attributes that are *directly drawn on this entity set*
#   * [primary_key] an unordered sublist of those attributes that are necessary to fully define the primary key of this entity set
#   * [connections] an unordered list of pairs (relationship_name, multiplicity) of all (non-IsA) relationships involving this entity set
#   * [parents] an unordered list of names of any entity sets that are parents of this entity set in an IsA relationship
#   * [supporting_relations] an unordered list of (non-IsA) relationship names that partially define this entity set 
class EntitySet:
    name = 'E'
    attributes = []
    primary_key = []
    connections = []
    parents = []
    supporting_relations = []
    def __init__(self, name, attributes, primary_key, connections, parents, supporting_relations):
        self.name = name
        self.attributes = attributes
        self.primary_key = primary_key
        self.connections = connections
        self.parents = parents
        self.supporting_relations = supporting_relations

# Corresponds to an entire Entity-Relationship Diagram (ERD)
# It consists of two member variables:
#   * [relationships] an unordered list of Relationship objects that are part of this ERD
#   * [entity_sets] an unordered list of EntitySet objects that are part of this ERD
class ERD:
    relationships = []
    entity_sets = []
    def __init__( self, relationships, entity_sets ):
        self.relationships = relationships
        self.entity_sets = entity_sets

# An example instantiation of the ERD class.
sample_erd = ERD( \
    [Relationship('R1',[],[])], \
    [EntitySet('A', ['a1','a2'], ['a1'], [('R1', Multiplicity.MANY)], [], []), \
     EntitySet('B', ['b1','b2'], ['b1'], [('R1', Multiplicity.MANY)], [], [])])

sample_erd2 = ERD( \
    [Relationship('R1',['x','y'],['x']), \
     Relationship('R2', [],[])], \
    [EntitySet('A', ['a1','a2','a3'], ['a1','a2'], [('R1', Multiplicity.MANY)], [], []), \
     EntitySet('B', ['b1','b2'], ['b1'], [('R1', Multiplicity.MANY)], [], ['R2']), \
     EntitySet('wB',['wb1'],['wb1'],[('R2', Multiplicity.ONE)], [] ,[])])

T1 = ERD([], [EntitySet('A', ['a1'], ['a1'], [], [], [])])

T2 = ERD([], [EntitySet('A', ['a1', 'a2'], ['a1', 'a2'], [], [], [])])

T5 = ERD( \
	[Relationship('R1', ['x'], ['x'])],
	[EntitySet('A', ['a1','a2'], ['a1'], [('R1', Multiplicity.MANY)], [], []),
	 EntitySet('B', ['b1','b2'], ['b1'], [('R1', Multiplicity.MANY)], [], [])])

T8 = ERD(
	[Relationship('R1', ['x'], ['x'])],
	[EntitySet('A', ['a1','a2'], ['a1'], [('R1', Multiplicity.MANY)], [], []),
	 EntitySet('B', ['b1','b2'], ['b1'], [('R1', Multiplicity.ONE)], [], [])]
)

T1 = ERD([], [EntitySet('A', ['a1'], ['a1'], [], [], [])])



# Test 3
T3 = ERD([], [EntitySet('A', ['a1', 'a2'], ['a1'], [], [], [])])



# Test 4
T4 = ERD(
	[Relationship('R1', [], [])],
	[EntitySet('A', ['a1','a2'], ['a1'], [('R1', Multiplicity.MANY)], [], []),
	 EntitySet('B', ['b1','b2'], ['b1'], [('R1', Multiplicity.MANY)], [], [])]
)


# Test 5
T5 = ERD(
	[Relationship('R1', ['x'], ['x'])],
	[EntitySet('A', ['a1','a2'], ['a1'], [('R1', Multiplicity.MANY)], [], []),
	 EntitySet('B', ['b1','b2'], ['b1'], [('R1', Multiplicity.MANY)], [], [])]
)


# Test 6
T6 = ERD(
	[Relationship('R1', ['x','y'], ['x'])],
	[EntitySet('A', ['a1','a2'], ['a1'], [('R1', Multiplicity.MANY)], [], []),
	 EntitySet('B', ['b1','b2'], ['b1'], [('R1', Multiplicity.MANY)], [], [])]
)


# Test 7
T7 = ERD(
	[Relationship('R1', [], [])],
	[EntitySet('A', ['a1','a2'], ['a1'], [('R1', Multiplicity.MANY)], [], []),
	 EntitySet('B', ['b1','b2'], ['b1'], [('R1', Multiplicity.ONE)], [], [])]
)

T9 = ERD([],\
    [EntitySet('A', ['a1','a2'], ['a1'], [], [], []), \
     EntitySet('B', ['b1','b2'], ['b1'], [], ['A'], [])])

T10 = ERD(
    [Relationship('R1', [], [])],
    [EntitySet('A', ['a1', 'a2'], ['a1'], [('R1', Multiplicity.ONE)], [], []),
      EntitySet('B', ['b1', 'b2'], ['b1'], [], [], ['R1'])])
    
T11 = ERD([],\
    [EntitySet('A', ['a1','a2'], ['a1'], [], [], []), \
     EntitySet('B', ['b1','b2'], ['b1'], [], ['A'], []),\
     EntitySet('C', ['c1','c2'], ['c1'], [], ['B'], [])])

T21 = ERD(
    [Relationship('R1', [], []),
     Relationship('R2', ['x'], []),
     Relationship('R3', [], [])],
    [EntitySet('A', ['a1'], ['a1'], [], [], ['R1']),
     EntitySet('B', ['b1'], ['b1'], 
         [('R1', Multiplicity.ONE), ('R2', Multiplicity.MANY)], [], ['R3']),
     EntitySet('C', ['c1'], ['c1'], [('R2', Multiplicity.ONE)], [], []),
     EntitySet('D', ['d1'], ['d1'], [('R3', Multiplicity.ONE)], [], [])]
)