import itertools
import random 

competitors = ['1', '2', '3', '4']

print (' '*40,"The competitors are: " + " ".join(competitors))
print(' '*38,'-'*31)

def do_round():
    
    matches = itertools.permutations(competitors,2)
    l=list(matches)
    
    random.shuffle(l) 
    for match in l: 
        print (' '*38,'|','   ',match[0],'     ','vs','     ', match[1],'   ','|')
        print(' '*38,'-'*31)
    return matches

do_round()