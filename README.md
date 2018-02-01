# CIS-890-Assignment-1

Formula will be generated using following arguments:

-n <N>: Number of non-empty clauses that should occur in a formula

-k <K>: Maximum number of variables that can occur in a formula

-l <L>: Maximum size of each clause

--strict: Every clause should have l literals (optional)

Instructions to generate CNF formula

1. Go to the folder where the groovy file *RandomFormulaGenerator.groovy* is.

2. Enter following command in CLI.

 groovy RandomFormulaGenerator.groovy -n N -k K -l L --strict

Note: Enter your constraint parameters in place of N, K and L by following definitions given above for each option. --strict option is not necessary to generate CNF formula. By specifying --strict, every clause contained in a generated formula will be having L literals.
