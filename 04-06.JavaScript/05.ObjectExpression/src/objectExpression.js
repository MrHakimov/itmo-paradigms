/**
 * @author: Muhammadjon Hakimov
 * created: 17.03.2019 19:23:45
 */

const variables = ['x', 'y', 'z'];

function Const(value) {
    this.value = value;
}
Const.prototype.evaluate = function () {
    return this.value;
};
Const.prototype.toString = function () {
    return this.value.toString();
};
Const.prototype.diff = function (diffVariable) {
    return new Const(0);
};

function Variable(name) {
    this.index = variables.indexOf(name);
}
Variable.prototype.evaluate = function () {
    return arguments[this.index];
};
Variable.prototype.toString = function () {
    return variables[this.index];
};
Variable.prototype.diff = function (diffVariable) {
    if (diffVariable === variables[this.index]) {
        return new Const(1);
    } else {
        return new Const(0);
    }
};

function Operation() {
    this.operands = arguments;
}
Operation.prototype.evaluate = function () {
    let result = [];
    for (let operand of this.operands) {
        result.push(operand.evaluate.apply(operand, arguments));
    }
    return this.calc.apply(null, result);
};
Operation.prototype.toString = function () {
    let result = "";
    for (let operand of this.operands) {
        result += operand.toString() + " ";
    }
    return result + this.operationLiteral;
};
Operation.prototype.diff = function (diffVariable) {
    let result = [];
    for (let operand of this.operands) {
        result.push(operand);
    }
    for (let operand of this.operands) {
        result.push(operand.diff(diffVariable));
    }
    return this.calcDiff.apply(null, result);
};

function Add(left, right) {
    Operation.call(this, left, right);
    this.operationLiteral = "+";
}
Add.prototype = Object.create(Operation.prototype);
Add.prototype.calc = (left, right) => {
    return left + right;
};
Add.prototype.calcDiff = (u, v, uDiff, vDiff) => {
    return new Add(uDiff, vDiff);
};

function Subtract(left, right) {
    Operation.call(this, left, right);
    this.operationLiteral = "-";
}
Subtract.prototype = Object.create(Operation.prototype);
Subtract.prototype.calc = (left, right) => {
    return left - right;
};
Subtract.prototype.calcDiff = (u, v, uDiff, vDiff) => {
    return new Subtract(uDiff, vDiff);
};

function Multiply(left, right) {
    Operation.call(this, left, right);
    this.operationLiteral = "*";
}
Multiply.prototype = Object.create(Operation.prototype);
Multiply.prototype.calc = (left, right) => {
    return left * right;
};
Multiply.prototype.calcDiff = (u, v, uDiff, vDiff) => {
    return new Add(new Multiply(uDiff, v), new Multiply(u, vDiff));
};

function Divide(left, right) {
    Operation.call(this, left, right);
    this.operationLiteral = "/";
}
Divide.prototype = Object.create(Operation.prototype);
Divide.prototype.calc = (left, right) => {
    return left / right;
};
Divide.prototype.calcDiff = (u, v, uDiff, vDiff) => {
    return new Divide(new Subtract(new Multiply(uDiff, v), new Multiply(u, vDiff)),
        new Multiply(v, v));
};

function Negate(operand) {
    Operation.call(this, operand);
    this.operationLiteral = "negate";
}

Negate.prototype = Object.create(Operation.prototype);
Negate.prototype.calc = (operand) => {
    return -operand;
};
Negate.prototype.calcDiff = (u, uDiff) => {
    return new Negate(uDiff);
};

function ArcTan(operand) {
    Operation.call(this, operand);
    this.operationLiteral = "atan";
}
ArcTan.prototype = Object.create(Operation.prototype);
ArcTan.prototype.calc = (operand) => {
    return Math.atan(operand);
};
ArcTan.prototype.calcDiff = (u, uDiff) => {
    return new Multiply(new Divide(new Const(1), new Add(new Const(1), new Multiply(u, u))), uDiff);
};

function ArcTan2(left, right) {
    Operation.call(this, left, right);
    this.operationLiteral = "atan2";
}
ArcTan2.prototype = Object.create(Operation.prototype);
ArcTan2.prototype.calc = (left, right) => {
    return Math.atan2(left, right);
};
ArcTan2.prototype.calcDiff = (u, v, uDiff, vDiff) => {
    return new Divide(new Subtract(new Multiply(v, uDiff), new Multiply(u, vDiff)),
        new Add(new Multiply(u, u), new Multiply(v, v)));
};

function Sinh(operand) {
    Operation.call(this, operand);
    this.operationLiteral = "sinh";
}
Sinh.prototype = Object.create(Operation.prototype);
Sinh.prototype.calc = (operand) => {
    return Math.sinh(operand);
};
Sinh.prototype.calcDiff = (u, uDiff) => {
    return new Multiply(new Cosh(u), uDiff);
};

function Cosh(operand) {
    Operation.call(this, operand);
    this.operationLiteral = "cosh";
}
Cosh.prototype = Object.create(Operation.prototype);
Cosh.prototype.calc = (operand) => {
    return Math.cosh(operand);
};
Cosh.prototype.calcDiff = (u, uDiff) => {
    return new Multiply(new Sinh(u), uDiff);
};

function Max5(...operands) {
    Operation.call(this, ...operands);
    this.operationLiteral = "max5";
}
Max5.prototype = Object.create(Operation.prototype);
Max5.prototype.calc = (...operands) => {
    return Math.max(...operands);
};

function Min3(...operands) {
    Operation.call(this, ...operands);
    this.operationLiteral = "min3";
}
Min3.prototype = Object.create(Operation.prototype);
Min3.prototype.calc = (...operands) => {
    return Math.min(...operands);
};

function isDigit(symbol) {
    return Boolean(symbol >= '0' && symbol <= '9');
}

const parse = expression => {
    let tokens = expression.split(/\s+/);

    let operations = {
        "+"      : [Add, 2],
        "-"      : [Subtract, 2],
        "*"      : [Multiply, 2],
        "/"      : [Divide, 2],
        "negate" : [Negate, 1],
        "atan"   : [ArcTan, 1],
        "atan2"  : [ArcTan2, 2],
        "sinh"   : [Sinh, 1],
        "cosh"   : [Cosh, 1],
        "max5"   : [Max5, 5],
        "min3"   : [Min3, 3]
    };

    let stack = [];
    tokens.map(function(token) {
        if (token in operations) {
            let args = [];
            let len = stack.length;
            stack.slice(len - operations[token][1], len).forEach(function () {
                args.push(stack.pop());
            });
            args.reverse();
            stack.push(new operations[token][0](...args));
        } else if (isDigit(token[0]) || (token[0] === '-' && token.length !== 1)) {
            stack.push(new Const(parseInt(token)));
        } else if (variables.indexOf(token) !== -1) {
            stack.push(new Variable(token));
        }
    });
    return stack.pop();
};
