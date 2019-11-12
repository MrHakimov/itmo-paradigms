/**
 * @author: Muhammadjon Hakimov
 * created: 10.03.2019 17:10:23
 */

const cnst = value => () => value;
const variable = name => (...values) => values[variables.indexOf(name)];

const abstractOperation = operation => (...operands) => (...values) => {
    let result = [];
    operands.map(function (currentOperand) {
        result.push(currentOperand(...values));
    });
    return operation(...result);
};

const add = abstractOperation((leftOperand, rightOperand) => leftOperand + rightOperand);
const subtract = abstractOperation((leftOperand, rightOperand) => leftOperand - rightOperand);
const multiply = abstractOperation((leftOperand, rightOperand) => leftOperand * rightOperand);
const divide = abstractOperation((leftOperand, rightOperand) => leftOperand / rightOperand);
const negate = abstractOperation(operand => -operand);

const med3 = abstractOperation((...operands) => {
    operands.sort(function(left, right){
        return left - right;
    });
    let len = operands.length;
    return operands[(len - (len % 2)) / 2];
});
const avg5 = abstractOperation((...operands) => {
    let sum = operands.reduce((sum, current) => sum + current);
    return sum / operands.length;
});

const abs = abstractOperation(operand => Math.abs(operand));
const iff = abstractOperation((first, second, third) => (first >= 0 ? second : third));

let variables = ['x', 'y', 'z'];
variables.forEach(function (currentVariable) {
    this[currentVariable] = variable(currentVariable);
});

let constants = {
    'one' : 1,
    'two' : 2,
    'pi'  : Math.PI,
    'e'   : Math.E
};
Object.keys(constants).forEach(function (key) {
    this[key] = cnst(constants[key]);
});

function isDigit(symbol) {
    return Boolean(symbol >= '0' && symbol <= '9');
}

const parse = expression => (...values) => {
    let tokens = expression.split(/\s+/);

    let operations = {
        '+'      : [add, 2],
        '-'      : [subtract, 2],
        '*'      : [multiply, 2],
        '/'      : [divide, 2],
        'negate' : [negate, 1],
        'med3'   : [med3, 3],
        'avg5'   : [avg5, 5],
        'abs'    : [abs, 1],
        'iff'    : [iff, 3]
    };

    let stack = [];
    tokens.forEach(function (token) {
        if (token in operations) {
            let args = [];
            let len = stack.length;
            stack.slice(len - operations[token][1], len).forEach(function () {
                args.push(stack.pop());
            });
            args.reverse();
            stack.push(operations[token][0].apply(this, args));
        } else if (isDigit(token[0]) || (token[0] === '-' && token.length !== 1)) {
            stack.push(cnst(parseInt(token)));
        } else if (variables.indexOf(token) !== -1 || token in constants) {
            stack.push(this[token]);
        }
    });
    return stack.pop()(...values);
};
