import sys


def lineNumber(argument):
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
    # Arrive here after all tests are accumulated.
    # foundKey corresponds to how many keywords were found.
    if (foundKey == 0):
        return False
    else:
        return True

#sys.argv[1] == first argument after script name when LineNumber.py is executed directly
if __name__ == "__main__":
    lineNumber(sys.argv[1])
