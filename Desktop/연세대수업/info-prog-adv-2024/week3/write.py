input_file = open('myfile.txt','r')

output_file = open('myfile_copy.txt','w')
line = input_file.readline() #다음 한줄을 읽는다.
while line != '':
    output_file.write(line)
    line = input_file.readline()
 
lines = input_file.readlines()
print(lines)

for line in lines:
    print(line)

input_file.close() #close 꼭 하기
output_file.close()
