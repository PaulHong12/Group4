input_file = open('emails_yonsei.txt','r')
output_file = open('emails_korea.txt','w')

lines = input_file.readlines()

for line in lines:
    part1 = line.split('@')[0]
    output_file.write(part1+"@korea.ac.kr\n")

input_file.close()
output_file.close()