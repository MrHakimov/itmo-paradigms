/**
 * @author: Muhammadjon Hakimov
 * created: 29.03.2019 21:34:51
 */
 
function Operation() {
    this.operands = arguments;
}

Operation.prototype.evaluate = function () {
    let result = [];
    for (let i = 0; i < this.operands.length; i++) {
        result.push(this.operands[i].evaluate.apply(this.operands[i], arguments));
    }
    return this.count.apply(null, result);
};

Operation.prototype.diff = function (name) {
    let argsArray = [];
    let diffArgsArray = [];
    for (let i = 0; i < this.operands.length; i++) {
        let operand = this.operands[i];
        argsArray.push(operand);
        diffArgsArray.push(operand.diff(name));
    }
    return this.countDiff.apply(null, (argsArray.concat(diffArgsArray)));
};

Operation.prototype.postfix = Operation.prototype.toString = function () {
    let result = "(";
    for (let i = 0; i < this.operands.length - 1; i++) {
        result += this.operands[i].postfix() + " ";
    }
    if (this.operands.length !== 0) {
        result += this.operands[this.operands.length - 1];
    }
    result += " " + this.operation + ")";
    return result;
};

function OperationFactory(literal, countFunc, countDiffFunc) {
    function Constructor() {
        let args = arguments;
        Operation.apply(this, args);
    }

    let operationPrototype = Object.create(Operation.prototype);
    operationPrototype.constructor = Constructor;
    operationPrototype.operation = literal;
    operationPrototype.count = countFunc;
    operationPrototype.countDiff = countDiffFunc;

    Constructor.prototype = operationPrototype;
    return Constructor;
}

function Const(value) {
    this.value = value;
}

Const.prototype.evaluate = function () {
    return this.value;
};
Const.prototype.diff = function () {
    return ZERO;
};
Const.prototype.postfix = Const.prototype.toString = function () {
    return this.value.toString();
};

const ZERO = new Const(0);
const HALF = new Const(0.5);
const ONE = new Const(1);
const TWO = new Const(2);

const VARIABLES = ['x', 'y', 'z'];

function Variable(name) {
    this.index = VARIABLES.indexOf(name);
}

Variable.prototype.evaluate = function () {
    return arguments[this.index];
};
Variable.prototype.diff = function (name) {
    if (VARIABLES[this.index] === name) {
        return ONE;
    } else {
        return ZERO;
    }
};
Variable.prototype.postfix = Variable.prototype.toString = function () {
    return VARIABLES[this.index];
};

const Add = OperationFactory('+',
    (first, second) => first + second,
    (u, v, uDiff, vDiff) => new Add(uDiff, vDiff)
);
const Subtract = OperationFactory('-',
    (first, second) => first - second,
    (u, v, uDiff, vDiff) => new Subtract(uDiff, vDiff)
);
const Multiply = OperationFactory('*',
    (first, second) => first * second,
    (u, v, uDiff, vDiff) => new Add(new Multiply(u, vDiff), new Multiply(uDiff, v))
);
const Divide = OperationFactory('/',
    (first, second) => first / second,
    (u, v, uDiff, vDiff) => new Divide(new Subtract(new Multiply(uDiff, v),
        new Multiply(u, vDiff)), new Multiply(v, v))
);
const Negate = OperationFactory('negate',
    (operand) => -operand,
    (u, uDiff) => new Negate(uDiff)
);

const Sum = OperationFactory('sum',
    (...args) => args.reduce((prefixSum, current) => prefixSum += current, 0),
    (...allArgs) => new Sum(...allArgs.slice(allArgs.length / 2))
);
const Sumsq = OperationFactory('sumsq',
    (...args) => args.reduce((prefixSum, current) => prefixSum += current * current, 0),
    (...allArgs) => new Sum(...allArgs.slice(0, allArgs.length / 2).map(
        (value, index) => new Multiply(allArgs[allArgs.length / 2 + index],
            new Multiply(value, TWO))))
);

const Length = OperationFactory('length',
    (...args) => Math.sqrt(args.reduce((curSum, x) => curSum += x * x, 0)),
    (...allArgs) => (allArgs.isEmpty() ? ZERO : new Multiply(new Divide(HALF,
        new Length(...allArgs.slice(0, allArgs.length / 2))), Sumsq.prototype.countDiff(...allArgs)))
);

const exceptions = function () {


    function newExceptions(exception, name) {
        exception.prototype = Object.create(Error.prototype);
        exception.prototype.name = name;
        exception.prototype.constructor = exception;
    }

    let ExceptionMessage = (expected, found, position) => "expected " + expected + " ,but found '" + found + "' on position " + (position + 1).toString();

    function ClosingBracketException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }
	
	function MissingClosingBracketException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }
	
	function MissingOperandException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }

    function MissingOperationException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }

    function InvalidNumberOfArgumentsException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }

    function InvalidEndingException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }

    newExceptions(ClosingBracketException, "ClosingBracketException");
	newExceptions(MissingClosingBracketException, "MissingClosingBracketException");
	newExceptions(MissingOperandException, "MissingOperandException");
    newExceptions(MissingOperationException, "MissingOperationException");
    newExceptions(InvalidNumberOfArgumentsException, "InvalidNumberOfArgumentsException");
    newExceptions(InvalidEndingException, "InvalidEndingException");

    return {
		ClosingBracketException: ClosingBracketException,
		MissingClosingBracketException: MissingClosingBracketException,
        MissingOperandException: MissingOperandException,
        MissingOperationException: MissingOperationException,
        InvalidNumberOfArgumentsException: InvalidNumberOfArgumentsException,
        InvalidEndingException: InvalidEndingException
    }
}();

const MissingOperandException = exceptions.MissingOperandException;
const MissingOperationException = exceptions.MissingOperationException;
const InvalidNumberOfArgumentsException = exceptions.InvalidNumberOfArgumentsException;
const InvalidEndingException = exceptions.InvalidEndingException;

Array.prototype.getLast = function () {
    return this[this.length - 1]
};
Array.prototype.isEmpty = function () {
    return this.length === 0
};

function isNumber(symbol) {
    return '0' <= symbol && symbol <= '9';
}
function isCorrectSymbol(c) {
    return !/\s/.test(c) && c !== "(" && c !== ")";
}

class Tokenizer {
    constructor(expression) {
        this.expression = expression;
        this.index = 0;
        this.skipWhiteSpaces();
    }

    nextToken() {
        let result = "";
        if (this.expression[this.index] === "(") {
            result += "(";
            this.index++;
            this.skipWhiteSpaces();
        }
        while (this.index < this.expression.length && isCorrectSymbol(this.expression[this.index])) {
            result += this.expression[this.index];
            this.index++;
        }
        this.skipWhiteSpaces();
        if (this.index < this.expression.length && this.expression[this.index] === ")") {
            result += ")";
            this.index++;
            this.skipWhiteSpaces();
        }
        return result;
    }

    getIndex() {
        return this.index;
    }

    skipWhiteSpaces() {
        while (this.index < this.expression.length && /\s/.test(this.expression[this.index])) {
            this.index++;
        }
    }
}

let argumentsCount = [];
let operations = [];
let isNameOfVariable = [];
for (let variable of VARIABLES) {
    isNameOfVariable[variable] = true;
}
let addOperation = function (literal, constructor, numberOfArgs) {
    operations[literal] = constructor;
    argumentsCount[literal] = numberOfArgs;
};
addOperation("+", Add, 2);
addOperation("-", Subtract, 2);
addOperation("*", Multiply, 2);
addOperation("/", Divide, 2);
addOperation("negate", Negate, 1);
addOperation("sumsq", Sumsq, Infinity);
addOperation("length ", Length, Infinity);

const parse = (expression) => expression.trim().split(/\s+/).reduce(functionParse, []).pop();
const functionParse = (stack, token) => {
    if (isNumber(token)) {
        stack.push(new Const(parseInt(token, 10)));
    } else if (isNameOfVariable[token] === true) {
        stack.push(new Variable(token));
    } else {
        stack.push(new operations[token](...stack.splice(-argumentsCount[token])));
    }
    return stack;
};

const parsePostfix = function (expression) {
    let allTokens = new Tokenizer(expression);
    let token;
    let args = [];
    let parsedArgs = [];
    let balance = 0;
    while ((token = allTokens.nextToken()) !== "") {
        let openingBracket = false;
        if (token[0] === "(") {
            openingBracket = true;
            token = token.substring(1);
            balance++;
        }
        let closingBracket = false;
        if (token[token.length - 1] === ")") {
            closingBracket = true;
            token = token.substring(0, token.length - 1);
            balance--;
            if (balance < 0) {
                throw new ClosingBracketException(token, allTokens.getIndex() - token.length);
            }
        }
        if (token === "length") {
            token += " ";
        }
        if (openingBracket) {
            if (operations[token] !== undefined && !closingBracket) {
                throw new InvalidNumberOfArgumentsException(token, allTokens.getIndex() - token.length);
            }
            parsedArgs.push(0);
        }
        if (closingBracket) {
            if (operations[token] === undefined) {
                throw new MissingOperationException(token, allTokens.getIndex() - token.length);
            }
            if (parsedArgs.isEmpty() || (argumentsCount[token] !== Infinity &&
                parsedArgs.getLast() !== argumentsCount[token])) {
                throw new InvalidNumberOfArgumentsException(token + ")", allTokens.getIndex() - token.length);
            }
            let currentArgs = args.splice(-parsedArgs.getLast());
            args.push(new operations[token](...currentArgs));
            parsedArgs.pop();
            if (!parsedArgs.isEmpty()) {
                parsedArgs[parsedArgs.length - 1]++;
            }
        }
        if (token !== "") {
            if (!isNaN(Number(token))) {
                args.push(new Const(parseInt(token, 10)));
                parsedArgs[parsedArgs.length - 1]++;
            } else if (isNameOfVariable[token] === true) {
                args.push(new Variable(token));
                parsedArgs[parsedArgs.length - 1]++;
            } else if (closingBracket === false) {
                throw new InvalidNumberOfArgumentsException(token, allTokens.getIndex() - token.length);
            }
        }
    }
    if (balance != 0) {
        throw new MissingClosingBracketException("at the end of expression " + token,
            allTokens.getIndex() - token.length);
    }
    if (args.length !== 1 || parsedArgs.length > 0) {
        throw new InvalidEndingException("unparsed arguments: " + args.join(", "),
            allTokens.getIndex() - token.length);
    }
    return args.pop();
};