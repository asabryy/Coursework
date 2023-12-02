import expense_calculator

THRESHOLD = 0.01;

person1 = [160, 159.64,  4];
person2 = [95,   89.53, 14];
person3 = [40,  225.20,  3];

population1 = [person2];
population3 = [person3, person2, person1];


def main():

    if (test_calc_mileage()):
        print("test_calc_mileage passed")
    else:
        print("test_calc_mileage failed at least one test")

    if (test_calc_hotel()):
        print("test_calc_hotel passed")
    else:
        print("test_calc_hotel failed at least one test")

    if (test_calc_food()):
        print("test_calc_food passed")
    else:
        print("test_calc_food failed at least one test")

    if (test_calc_total_expense()):
        print("test_calc_total_expense passed")
    else:
        print("test_calc_total_expense failed at least one test")

    if (test_calc_all_expenses()):
        print("test_calc_all_expenses passed")
    else:
        print("test_calc_all_expenses failed at least one test")


def test_calc_mileage():
    kms_drive = 0
    result = 0
        
    result = expense_calculator.calc_mileage(kms_drive)
    #print("should be approx 0: ",  result, ":", abs(result-0))
    if (not(abs(result-0)<THRESHOLD)):
        return False

    kms_drive = 21.7
    result = expense_calculator.calc_mileage(kms_drive)
    #print("should be approx 5.21: ",  result)
    if (not(abs(result-5.21)<THRESHOLD)):
        return False

    kms_drive = 100
    result = expense_calculator.calc_mileage(kms_drive)
    #print("should be approx 24: ",  result)
    if (not(abs(result-24.0)<THRESHOLD)):
        return False
    
    kms_drive = 101.5
    result = expense_calculator.calc_mileage(kms_drive)
    #print("should be approx 24.72: ",  result)
    if (not(abs(result-24.72)<THRESHOLD)):
        return False

    return True

def test_calc_food():
    
    result = 0
    
    days = 1
    result = expense_calculator.calc_food(days)
    #print("should be approx 65.0: ", result)
    if (not(abs(result-65)<THRESHOLD)):
        return False

    days = 4
    result = expense_calculator.calc_food(days)
    #print("should be approx 260.0: ", result)
    if (not(abs(result-260)<THRESHOLD)):
        return False

    days = 7
    result = expense_calculator.calc_food(days)
    #print("should be approx 345.0: ", result)
    if (not(abs(result-345)<THRESHOLD)):
        return False

    days = 13
    result = expense_calculator.calc_food(days)
    #print("should be approx 640.71: ", result)
    if (not(abs(result-640.71)<THRESHOLD)):
        return False

    return True

def test_calc_hotel():
    result = 0

    days = 5
    rate = 99.97
    result = expense_calculator.calc_hotel(rate, days)
    #print("should be approx 451.86:", result)
    if (not(abs(result-451.86)<THRESHOLD)):
        return False

    days = 3
    rate = 172.56
    result = expense_calculator.calc_hotel(rate, days)
    #print("should be approx 389.99:", result)
    if (not(abs(result-389.99)<THRESHOLD)):
        return False

    return True

def test_calc_total_expense():
    result = 0

    result = expense_calculator.calc_total_expense(person1)
    #print("should be approx 853.98:", result)
    if (not(abs(result-853.98)<THRESHOLD)):
        return False

    result = expense_calculator.calc_total_expense(person2)
    #print("should be 2028.0:", result)
    if (not(abs(result-2028.0)<THRESHOLD)):
        return False
    result = expense_calculator.calc_total_expense(person3)
    #print("should be 713.55:", result)
    if (not(abs(result-713.55)<THRESHOLD)):
        return False

    return True

def test_calc_all_expenses():
    result1 = []
    expected1 = [2027]

    expense_calculator.calc_all_expenses(population1, result1)
    #print("should be:", expected1)
    #print("is:", result1)

    if (result1 != expected1):
        return False

    result2 = []
    expected2 = [713, 2027, 853]

    expense_calculator.calc_all_expenses(population3, result2)
    #print("should be:", expected2)
    #print("is:", result2)

    if (result2 != expected2):
        return False

    return True



if __name__ == '__main__':
    main()

