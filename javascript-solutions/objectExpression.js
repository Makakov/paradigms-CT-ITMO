// :NOTE: не проходят тесты
"use strict"

const map = {
    "x": 0,
    "y": 1,
    "z": 2
};

const BinaryOperators = ["+", "-", "/", "*"];

const UnaryOperators = ["negate", "sin", "cos"];

const MultiOperators = ["var", "mean"];

function Binary(left, right, operation, operator) {
    this.left = left;
    this.right = right;
    this.operation = operation;
    this.operator = operator;
}

Binary.prototype.evaluate = function (...args) {
    return this.operation(this.left.evaluate(...args), this.right.evaluate(...args));
}

Binary.prototype.toString = function () {
    return this.left.toString() + " " + this.right.toString() + " " + this.operator;
}

Binary.prototype.prefix = function () {
    return "(" + this.operator + " " + this.left.prefix() + " " + this.right.prefix() + ")";
}

function AbstractOperation(left, right, operation, operator) {
    Binary.call(this, left, right, operation, operator);
}

AbstractOperation.prototype = Object.create(Binary.prototype);

const binaryFactory = (operation, sign) => function (left, right) {
    return new AbstractOperation(left, right, operation, sign)
}

const Add = binaryFactory((a, b) => a + b, "+");

const Subtract = binaryFactory((a, b) => a - b, "-");

const Divide = binaryFactory((a, b) => a / b, "/");

const Multiply = binaryFactory((a, b) => a * b, "*");

function Unary(operand, operation, name) {
    this.operand = operand;
    this.operation = operation;
    this.name = name;
}

Unary.prototype.evaluate = function (...args) {
    return this.operation(this.operand.evaluate(...args))
}

Unary.prototype.toString = function () {
    return this.operand.toString() + " " + this.name;
}

Unary.prototype.prefix = function () {
    return "(" + this.name + " " + this.operand.prefix() + ")";
}

function AbstractUnary(operand, operation, name) {
    Unary.call(this, operand, operation, name);
}

AbstractUnary.prototype = Object.create(Unary.prototype);

const unaryFactory = (operation, sign) => function (operand) {
    return new AbstractUnary(operand, operation, sign)
}

const Negate = unaryFactory((a) => -a, "negate");

const Sin = unaryFactory(Math.sin, "sin");

const Cos = unaryFactory(Math.cos, "cos");

function Const() {
    this.cnst = Number(arguments[0]);
}

Const.prototype.evaluate = function () {
    return this.cnst;
}

Const.prototype.toString = function () {
    return this.cnst.toString();
}

Const.prototype.prefix = function () {
    return this.cnst.toString();
}

function Variable() {
    this.variable = arguments[0];
}

Variable.prototype.evaluate = function (...args) {
    return args[map[this.variable]];
}

Variable.prototype.toString = function () {
    return this.variable;
}

Variable.prototype.prefix = function () {
    return this.variable;
}

function AggregateOperation(operationType, ...args) {
    this.operationType = operationType;
    this.args = args;
}

AggregateOperation.prototype.evaluate = function (...vars) {
    switch (this.operationType) {
        case "mean":
            let sum = 0;
            for (let arg of this.args) {
                sum += arg.evaluate(...vars);
            }
            return sum / this.args.length;
        case "var":
            let mean = new AggregateOperation("mean", ...this.args).evaluate(...vars);
            let res = 0;
            for (let arg of this.args) {
                res += Math.pow(arg.evaluate(...vars) - mean, 2);
            }
            return res / this.args.length;
        default:
            throw new Error("wrong operator");
    }
};

AggregateOperation.prototype.prefix = function () {
    let str = this.args.map(arg => arg.prefix()).join(" ");
    return "(" + this.operationType + " " + str + ")";
};

function Mean(...args) {
    return new AggregateOperation("mean", ...args);
}

function Var(...args) {
    return new AggregateOperation("var", ...args);
}
// :NOTE: копипаста

function TokenizeBinary(left, right, token) {
    const binMap = {
        "+": new Add(left, right),
        "-": new Subtract(left, right),
        "/": new Divide(left, right),
        "*": new Multiply(left, right)
    };
    return binMap[token];
}

function TokenizeUnary(operand, token) {
    const unaryMap = {
        "negate": new Negate(operand),
        "sin": new Sin(operand),
        "cos": new Cos(operand)
    };
    return unaryMap[token];
}

const parse = function (expr) {
    let expression = expr.split(" ");
    let stack = [];
    expression.forEach(token => {
        if (token === "") {
            return;
        }
        if (BinaryOperators.includes(token)) {
            let left = stack.pop();
            let right = stack.pop();
            stack.push(TokenizeBinary(right, left, token));
        } else if (UnaryOperators.includes(token)) {
            let operand = stack.pop();
            stack.push(TokenizeUnary(operand, token));
        } else if (token in map) {
            stack.push(new Variable(token));
        } else {
            stack.push(new Const(token));
        }
    });
    return stack.pop();
}

function checkBalance(string) {
    let balance = 0;
    for (let i = 0; i < string.length; i++) {
        if (string[i] === "(") {
            balance++;
        } else if (string[i] === ")") {
            balance--;
        }
    }
    return balance;
}

function pushInStack(num, token, stack, map) {
    stack.push(token);
    map.push(num);
}

function recursiveParse(expression) {
    if (expression[0] !== "(" && !isFinite(Number(expression[0])) && !(expression[0] in map)) {
        throw new Error("no opening bracket");
    }
    let stack = [];
    let operatorsMap = [];
    let cntOpen = 0;
    let cntClose = 0;
    let idx = -1;
    let waitForOperator = [];
    waitForOperator.push(false);
    expression.forEach(token => {
        idx++;
        let flag = waitForOperator.pop();
        if (BinaryOperators.includes(token)) {
            pushInStack(2, token, stack, operatorsMap);
            return;
        } else if (UnaryOperators.includes(token)) {
            pushInStack(1, token, stack, operatorsMap);
            return;
        } else if (MultiOperators.includes(token)) {
            pushInStack(3, token, stack, operatorsMap);
            return;
        } else if (flag === true && token !== "(") {
            throw new Error("no operator");
        }
        waitForOperator.push(flag);
        if (token === ")") {
            cntClose++;
            let operationIndex = operatorsMap.pop();
            switch (operationIndex) {
                case 1:
                    let operand = stack.pop();
                    let operator = stack.pop();
                    if (operand === undefined || operator === undefined) {
                        throw new Error("no operands");
                    }
                    stack.push(TokenizeUnary(operand, operator));
                    break;
                case 2:
                    let left = stack.pop();
                    let right = stack.pop();
                    let op = stack.pop();
                    if (!BinaryOperators.includes(op)) {
                        // :NOTE: малоинформативно
                        throw new Error("wrong operator");
                    }
                    stack.push(TokenizeBinary(right, left, op));
                    break;
                case 3:
                    let args = [];
                    let num = stack.pop();
                    while (!MultiOperators.includes(num)) {
                        args.push(num);
                        num = stack.pop();
                    }
                    if (num === "mean") {
                        stack.push(new AggregateOperation("mean", ...args.reverse()));
                    } else if (num === "var") {
                        stack.push(new AggregateOperation("var", ...args.reverse()));
                    }
                    break;
                default:
                    if (cntClose !== cntOpen) {
                        throw new Error("wrong parenthesis");
                    }
            }
        } else if (token !== "(") {
            if (token in map) {
                stack.push(new Variable(token));
            } else if (isFinite(Number(token))) {
                stack.push(new Const(token));
            } else {
                throw new Error("wrong variable/const type");
            }
        } else if (token === "(") {
            if (!BinaryOperators.includes(expression[idx + 1]) && !UnaryOperators.includes(expression[idx + 1]) &&
            !MultiOperators.includes(expression[idx + 1]) && expression[idx + 1] !== "(") {
                throw new Error("no operator");
            }
            waitForOperator.push(true);
            cntOpen++;
        }
    });
    if (stack.length > 1) {
        return recursiveParse(stack);
    } else if (stack.length === 0) {
        throw new Error("");
    }
    return stack.pop();
}

function parsePrefix(string) {
    //println(string);
    if (checkBalance(string) !== 0) {
        throw new Error("wrong parenthesis");
    }
    let expression = string.split(/(\s+|\(|\))/).filter(part => part.trim() !== '');
    return recursiveParse(expression);
}

// let expression = "(negate)";
// console.log(parsePrefix(expression));