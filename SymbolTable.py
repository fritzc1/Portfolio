import sys



############### MAIN Methods ###############

def lineNumber(argument):
    if (len(sys.argv[]) != 2):
        System.out.print
    inFile = open(argument,'r')
    
    lineCounter = 0
    # For future reference: labelReferenceTable = {}

    for line in inFile:
        # Removing newline character for easier printing later
        currentLine = line.replace("\n", "")
        # Test all conditions where line would not receive a number
        comment = onlyCommentCheck(currentLine)
        label = onlyLabelCheck(currentLine)
        whitespace = onlyWhitespaceCheck(currentLine)
        keyword = keywordCheck(currentLine)
        if (comment | label | whitespace | keyword):
            print ("\t> ", currentLine)
        else:
            print (lineCounter, "\t> ", currentLine)
            lineCounter = lineCounter + 1
    return



############### Accessor Methods ###############
# These methods are used to determine whether a line requires a number

# Returns TRUE if single line contains ONLY comment.
# Line can be inported in either original format or stripped.
def onlyCommentCheck(inputLine):
    if (";" in inputLine):
        # Remove all whitespaces
        inputLine = inputLine.replace("\t", " ")
        inputLine = inputLine.replace(" ", "")
        # Split comment off
        segmentedLine = inputLine.split(";", 1)
        # Remove empty elements
        if ("" in segmentedLine):
            segmentedLine.remove("")
        # If there is exactly one element, it is a comment
        if (len(segmentedLine) == 1):
            return True
        else:
            return False
    else:
        return False

# Return TRUE if there is no code after last label.
# Line can be inported in either original format or stripped
# Any comments are removed before test!
def onlyLabelCheck(inputLine):
    if (":" in inputLine):
        # Remove all whitespaces
        inputLine = inputLine.replace("\t", " ")
        inputLine = inputLine.replace(" ", "")
        # Remove comments
        if (";" in inputLine):
            segmentedLine = inputLine.split(";", 1)
            inputLine = segmentedLine[0]
        # Split at rightmost label)
        segmentedLine = inputLine.rsplit(":", 1)
        # Remove empty elements
        if ("" in segmentedLine):
            segmentedLine.remove("")
        # All labels are grouped into exactly one token
        if (len(segmentedLine) == 1):
            return True
        else:
            return False
    else:
        return False

# Returns TRUE if only whitespace or no characters on line.
# Line can be inported in either original format or stripped.
def onlyWhitespaceCheck(inputLine):
    if (len(inputLine) == 0):
        return True
    else:
        #return true if only whitespace and at least 1 char
        return inputLine.isspace() 

# Returns TRUE if ANY keywords in KEYWORD list are present in input.
# Line can be imported in either original format or stripped.
def keywordCheck(inputLine):
    keywords = ["public", "extern", "end"]
    foundKey = 0
    for element in keywords:
        if (inputLine.find(element) != -1):
            foundKey = foundKey + 1
    # Arrive here after all test results are accumulated.
    # foundKey corresponds to how many keywords were found.
    if (foundKey == 0):
        return False
    else:
        return True



############### Modifier Methods ###############
    
# Strips comment and whitespace and returns line
def stripComment(line):
    L = line.split(;)
    return (L[0].strip())

# Strips whitespace for a list
def stripList(L):
    for i in range(len(L)):
        L[i] = L[i].strip()
    return L

# returns a list [T/F, list of labels, rest of line (could be empty)]
# return True if something left in line after separating labels
def parse(line):
    L = line.split(';', 1)
    L = L[0]
    L = line.split(':')
    L = stripList(L)
    if len(L) == 1:
    	# no labels
    	return [True,[],L[0]]
    if L[-1] == "":
    	# labels only
    	return [False,L[0:-1],""]
    # labels and some more
    return	[True, L[0:-1], L[-1]]
    
if __name__ == "__main__":
    if (sys.argv[2] != Null):
        raise RuntimeError("Cannot have more than one filename argument")
    lineNumber(sys.argv[1])
