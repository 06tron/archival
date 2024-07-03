let a = Math.sqrt(2);
let b = 1/a;
let len = b+Math.sqrt(1-b);
let c = Math.sqrt(2-a)/2;
let d = Math.sqrt(2+a)/2;
let polygon = [[0, 0], [0, len], [d, len+c], [len+c, d], [len, 0]];
console.log(JSON.stringify(polygon));
