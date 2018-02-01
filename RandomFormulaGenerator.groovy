package RandomSat

import groovy.transform.Field

@Field int numberOfNonEmptyClauses
@Field int maxNumberOfVars
@Field int maxSizeOfClause
@Field Random random = new Random()
@Field allFormulas = []
@Field strictNumOfVars
@Field List formula = []


void gettersFromCommandLine() {
    numberOfNonEmptyClauses = Integer.parseInt(args[1])
    maxNumberOfVars = Integer.parseInt(args[3])
    maxSizeOfClause = Integer.parseInt(args[5])
    try {
        strictNumOfVars = args[6]
    }
    catch (ArrayIndexOutOfBoundsException e) {
        println("Procceding to generate without --strict \n")
    }

}

gettersFromCommandLine()
generateFormula()

void generateFormula() {
    def loopCounter = 1
    while ({
        def clause = getOneClause()
//        if (!isRedundant(clause)) {
            formula << clause
            ++loopCounter
//        }
        loopCounter <= numberOfNonEmptyClauses
    }());

        for (List itr : formula) {
            println(itr)
        }
    if (formula.size() > 0) {
        writeToFile()
    }
}

List getOneClause() {
    try {
        randomlyGeneratedNumber = random.nextInt(maxNumberOfVars) + 1
    }
    catch (IllegalArgumentException e) {
        boundError()
    }
    def randomlyGeneratedSignedNumber = 0
    def generatedClause = []
    def sizeOfClause = 0
    if (strictNumOfVars == "--strict") {
        sizeOfClause = maxSizeOfClause
    } else {
        try {
            sizeOfClause = random.nextInt(maxSizeOfClause) + 1 // This will be shadowed by setting strict
        } catch (IllegalArgumentException e) {
            boundError()
        }
    }
    while (generatedClause.size() < sizeOfClause) {
        def randomlyGeneratedNumber = 0
        try {
            randomlyGeneratedNumber = random.nextInt(maxNumberOfVars) + 1
        }
        catch (IllegalArgumentException e) {
            boundError()
        }
        def signOfVar = random.nextInt(2)
        switch (signOfVar) {
            case 0:
                randomlyGeneratedSignedNumber = randomlyGeneratedNumber
                break
            case 1:
                randomlyGeneratedSignedNumber = -1 * randomlyGeneratedNumber
                break
            default:
                print("Impossible by documentation!!!")
        }
        if (!generatedClause.contains(-1 * randomlyGeneratedSignedNumber)) {
            generatedClause << randomlyGeneratedSignedNumber
        }
    }
    return generatedClause
}

void boundError() {
    println("Bound on all inputs must be postiive")
    numberOfNonEmptyClauses = 0
    maxSizeOfClause = 0
}

void writeToFile() {
    def fileNumber = 1
    def cnfFile
    def filename
    while ({
        filename = "RandomCnfFormula" + fileNumber + ".cnf"
        cnfFile = new File(filename)
        ++fileNumber
        cnfFile.exists()
    }());
    cnfFile << "c " + filename
    cnfFile << System.getProperty("line.separator").toString()
    cnfFile << "p cnf " + numberOfNonEmptyClauses.toString() + " " + maxNumberOfVars.toString() + " " + maxSizeOfClause.toString()
    for (List oneClauseAtATime : formula) {
        cnfFile << System.getProperty("line.separator").toString()
        for (int literal : oneClauseAtATime) {
            cnfFile << literal.toString() + " "
        }
        cnfFile << "0"
    }
}

boolean isRedundant(List clause) {
    def flag = false
    redundancy:
    {
        for (List itr1 : formula) {
            if (itr1.size() == clause.size()) {
                def innerFlag = false//if size is same then check further
                for (int itr2 : itr1) {
                    innerFlag = innerFlag && clause.contains(itr2)
                    if (!innerFlag) break
                }
                if (flag == true) {
                    break redundancy
                }
            }
        }
    }
    return flag
}