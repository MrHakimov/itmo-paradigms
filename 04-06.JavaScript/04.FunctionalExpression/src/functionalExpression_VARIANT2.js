/**
 * @authors: Daniel Korolev and Muhammadjon Hakimov
 * created:  24.03.2019 21:49:05
 */
 
const abstractOperation =
    op => (...operands) => (...values) => op(...operands.map(curOperand => curOperand(...values)));

const cnst = value => () => value;

const variable = name => (...values) => values[name.charCodeAt(0) - "x".charCodeAt(0)];

const add = abstractOperation((a, b) => a + b);

const subtract = abstractOperation((a, b) => a - b);

const multiply = abstractOperation((a, b) => a * b);

const divide = abstractOperation((a, b) => a / b);

const negate = abstractOperation(a => -a);

const avg5 = abstractOperation((...operands) => operands.reduce((sum, cur) => sum + cur) / operands.length);

const med3 = abstractOperation((...operands) =>
    operands.sort((a, b) => a - b)[~~(operands.length / 2)]);

const pi = cnst(Math.PI);
const e = cnst(Math.E);

const consts = {
    'pi': pi,
    'e': e,
};

const vars = {
    'x': variable('x'),
    'y': variable('y'),
    'z': variable('z'),
};

const operations = {
    '+': [add, 2],
    '-': [subtract, 2],
    '*': [multiply, 2],
    '/': [divide, 2],
    'negate': [negate, 1],
    'med3': [med3, 3],
    'avg5': [avg5, 5],
};

const parse = expression => (...values) => {
    let tokens = expression.split(' ').filter(token => token !== "");
    let stack = [];
    tokens.map(token => {
        if (token in operations) {
            let [op, numberOfArgs] = operations[token];
            stack.push(op(...stack.splice(stack.length - numberOfArgs, numberOfArgs)));
        } else if (token in vars) {
            stack.push(vars[token]);
        } else if (token in consts) {
            stack.push(consts[token]);
        } else {
            stack.push(cnst(Number(token)));
        }
    });
    return stack.pop()(...values);
};
