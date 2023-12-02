n = 100

number_of_function_calls = 0

for i in range(1, n+1):
	for j in range(1, i+1):
		for k in range(1, j+1):
			number_of_function_calls += 1
			
print("The number of function calls was", number_of_function_calls)