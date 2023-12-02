import numpy as np

# Calculation constants:
GST                 = 5.0   # Good and Service tax rate
HOTEL_TAX           = 8.0   # Hotel tax rate
LOW_MILEAGE_RATE    = 0.24  # $ per km mileage rate
HIGH_MILEAGE_RATE   = 0.48  # $ per km mileage rate

DAYS_PER_WEEK       = 7;    # $7 days in a week
DAILY_FOOD_RATE     = 65;   # daily food expense rate
WEEKLY_FOOD_RATE    = 345;  # weekly food expense rate

# result array access constants:
KMS_DRIVE_INDEX      = 0
HOTEL_COST_INDEX     = 1
NUM_DAYS_INDEX       = 2


def calc_mileage (kms):
    """ Purpose: calculates the mileage cost given the number of km driven
        NOTE:LOW_MILEAGE_RATE applied to kms up to and including 100 kms
        HIGH_MILEAGE_RATE applied to kms over 100 kms
        
    Args:
        kms (double): number of kms driven
        
    Preconditions: kmx>=0
        
    Returns: double - the amount of calculated mileage expense
    """
    mileage_expense = 0

    if (kms <= 100):
        mileage_expense = kms * LOW_MILEAGE_RATE
    else:
            mileage_expense = 100 * LOW_MILEAGE_RATE + (kms-100) * HIGH_MILEAGE_RATE
    
    return mileage_expense

def calc_hotel (rate, num_days):
    """ Purpose: calculates the hotel cost given the rate and number of days in the city
        
           NOTE: hotels charge both GST and Hotel tax and should be calculated
              and added to the expense total returned.
        
    Args:   rate (double) - nightly rate of the hotel
            num_days (int) - number of days of travel
    
    Preconditions: the number of days is one less than the number of hotel nights used
                   and >= 1
        
    Returns: double - the amount of calculated hotel expense
    """
    cost = rate * (num_days-1)
        
    tax = cost * (GST + HOTEL_TAX)/100
                
    return cost + tax

def calc_food (num_days):
    """ Purpose: calculates the food cost given the number of days of travel
    NOTE:   if travel is less than 7 days uses DAILY_FOOD_RATE,
            if travel is 7 or more days uses WEEKLY_FOOD_RATE
            with for less than full weeks
            ie. if travel is 9 days, rate will be:
            one week at WEEKLY_FOOD_RATE + 2/7 of the WEEKLY_FOOD_RATE
        
    
    Args:  num_days (int) - number of days of travel

    Preconditions: num_days is >= 1
        
    Returns: double - the amount of calculated hotel expense
    """
    expense = 0
                
    if (num_days>=DAYS_PER_WEEK ):
        expense = num_days/DAYS_PER_WEEK * WEEKLY_FOOD_RATE
                        
    else:
        expense = num_days * DAILY_FOOD_RATE
                                
    return expense

def calc_total_expense (data):
    """ Purpose: calculates the expenses for a person as:
    the sum of mileage, hotel and food costs

    Args:  data (double[]) - an array of data from a single person at the following indices:
            [KMS_DRIVE_INDEX]  - the number of kms they drove
            [HOTEL_COST_INDEX] - the cost of the hotel per night
            [NUM_DAYS_INDEX]   - the number of days they were traveling

    Preconditions: data must be of length 3 with expected values at indices listed above
        
    Returns: double - the amount of calculated hotel expense
    """
                
    mileage = calc_mileage(data[KMS_DRIVE_INDEX])
    hotel = calc_hotel(data[HOTEL_COST_INDEX],int (data[NUM_DAYS_INDEX]))
    food = calc_food(int (data[NUM_DAYS_INDEX]))
                
    result = mileage + hotel + food
                    
    return result

def calc_all_expenses(data, expense_results):
    """ Purpose: calculates the expenses for each person in a population
    and stores the result in the corresponding index of the 1D result array

    Args:  data (double[]) - a 2D array of data for a populution where,
        each row represents a person in the population and
        each column of a row has the following data for that individual:
            [KMS_DRIVE_INDEX]  - the number of kms they drove
            [HOTEL_COST_INDEX] - the cost of the hotel per night
            [NUM_DAYS_INDEX]   - the number of days they were traveling
        expense_results (int[]) - a 1D array to store the total expense for each individual
        NOTE: the decimal portion of the values stored in the result array will be truncated
 
    Preconditions: the data array must have 3 columns and
        the length of the 1D result array must be
        equal to the number of rows in the data array
        
    Returns: void
    """
    for person in data:
        expense_results.append(int(calc_total_expense(person)))






