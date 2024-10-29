"use strict"

const cnst = (a) => () => a;

const pi = cnst(Math.PI);

const e = cnst(Math.E)

const evaluate = (func) => (a, b) => (...args) => func(a(...args), b(...args));

const unary = (func) => (a) => (...args) => func(a(...args));

const square = unary((a) => a * a);

const sqrt = unary((a) => Math.sqrt(a));

const negate = unary((a) => -a);

const variable = variable => (...args) => {
    const map = {
        "x": 0,
        "y": 1,
        "z": 2
    };
    return args[map[variable]];
}

const add = evaluate((a, b) => a + b);

const subtract = evaluate((a, b) => a - b);

const multiply = evaluate((a, b) => a * b);

const divide = evaluate((a, b) => a / b);

let expression = subtract(
    multiply(
        cnst(2),
        variable("x")
    ),
    cnst(3)
);

//console.log(expression(0, 5, 0));