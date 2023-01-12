// Slot: { val: natural, rep: [natural] }

// slot_system: (max: natural) => {
//		get_slot: (n: natural) => Slot,
//		is_prime: (s: Slot) => boolean,
//		period: () => natural
// }
function slot_system(max) {
	let a = [3];
	let product = 3;
	while (product <= max) {
		let next = a[a.length - 1];
		let prime = false;
		while (!prime) {
			next += 2;
			prime = true;
			for (let i = 0; i < a.length; i++) {
				if (next % a[i] === 0) {
					prime = false;
				}
			}
		}
		a.push(next);
		product *= next;
	}
	return {
		// 'n' should be a natural less than or equal to 'max'
		get_slot: n => ({ val: n, rep: a.map(x => n % x) }),
		// 's' should be greater than 2
		is_prime: s => s.val % 2 === 1 && !s.rep.includes(0) || a.includes(s.val),
		period: () => product
	};
}

// is_prime: (n: natural) => boolean
function is_prime(n) {
	if (n === 2 || n === 3) {
		return true;
	}
	if (n <= 1 || n % 2 === 0 || n % 3 === 0) {
		return false;
	}
	for (let i = 5; i * i <= n; i += 6) {
		if (n % i === 0 || n % (i + 2) === 0) {
			return false;
		}
	}
	return true;
}

// let max = 1154;
// let sys = slot_system(max);
// for (let i = 3; i < max; i++) {
// 	let s = sys.get_slot(i);
// 	if (sys.is_prime(s) !== is_prime(s.val)) {
// 		console.log(s.rep + ": " + s.val);
// 	}
// }
// console.log(f(max).rep + ": " +  max);

let n = 3;
let d = 3;
let sys;
for (let i = 3; i < 10000; i++) {
	if (i === n) {
		sys = slot_system(n);
		n = sys.period();
	}
	let s = sys.get_slot(i);
	if (sys.is_prime(s) !== is_prime(s.val)) {
		console.log(d + ": " + s.val);
		d = 0;
	}
	d++;
}

// example of a slot: [1, 1, 2, 0] (equal to decimal 121)
// for a given 'max', every natural less than or equal to 'max' is represented by an slot with length 'l'.
// the product of the first 'l' odd primes must be greater than 'max'. (ex: if 'l' is  4, then 'max' is less than 3*5*7*11)
// if an odd 
