### Iteration 3 Design ###

For Iteration 3, the goal is to make this design a bit easier to handle, with evaluation responsibilities spread out to multiple classes.  The general structure will be to Tokenize the string, form the tokens into an expression tree, and evaluate the expression tree, with separate classes to represent the Tokenizer, TreeBuilder, and TreeNodes, and another class to aggregate these classes and drive the process.  To facilitate easy integration into the system, the driver will share an interface with the existing MathEvaluator class, allowing easy refactoring of the code.

The Driver is called EquationEvaluator.  It contains an EquationTreeBuilder and an EquationTokenizer.  The equation tokenizer breaks an expression into an array of tokens which it gives to an EquationScanner object which is immediately given to the TreeBuilder.  The TreeBuilder then receives the tokens one by one from the scanner and creates an equation tree.  Finally it balances the tree to account for order of operations and calls the recursive get value operation on the root of the tree.





### Iteration 2 Design ###


The following link leads to a class diagram for the Iteration 2 form of the model.  The structure essentially revolves around Expressions (objects that basically wrap a string holding the input to the calculator) and MathEvaluator, which evaluates the value of an Expression.  Math Evaluator holds 2 internal classes, Node and Operator, but basically acts as a mega class for expression evaluation, with pretty much all of the parsing and evaluating happening within the class and or its contained classes.

[Iteration 2 class diagram](http://www.duke.edu/~bnm2/graphingcalculator/graphcalc_model_class_full_It2.jpg)