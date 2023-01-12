SVG.on(document, 'DOMContentLoaded', function() {
    var draw = SVG().addTo('body').size(1020, 1020)
	fillSlice(0, 0, 0, 0, 0, 0, 0, draw)
})

//	class Square {
//		xp: boolean, true if x-axis points in a positive screen direction (east or south)
//		yp: boolean, true if y-axis points in a positive screen direction (east or south)
//		xh: boolean, true if x-axis points in a horizontal screen direction (east or west)
//		asRelation: (c: Cardinal) => Square
//		applyRelation: (f: Square, c: Cardinal) => Square, takes a relation function and applies it in a given direction
//		index: () => natural
//		toString: () => string
//	}

// square: (hPos: boolean, vPos: boolean, xHorz: boolean) => Square
function square(xPos, yPos, xHorz) {
	return {
		xp: xPos,
		yp: yPos,
		xh: xHorz,
		asRelation: c => square(xPos == c.ch, yPos == c.ch, xHorz),
		applyRelation: (f, c) => square(xPos == c.ch == f.xp, yPos == c.ch == f.yp, xHorz == f.xh),
		index: () => (xPos ? 0 : 1) + (yPos ? 0 : 2) + (xHorz ? 0 : 4),
		toString: () => [xPos, yPos, xHorz].map(b => b ? 'Y' : 'N').join(' ')
	}
}  

//	class Cardinal {
//		p: boolean, true if cardinal points in a positive screen direction (east or south)
//		h: boolean, true if cardinal points in a horizontal screen direction (east or west)
//		opposite: () => Cardinal
//		transferTo: (s: Square) => Cardinal
//		index: () => natural
//		toString: () => string
//	}

// cardinal: (pos: boolean, horz: boolean) => Cardinal
function cardinal(cPos, cHorz) {
	return {
		cp: cPos,
		ch: cHorz,
		opposite: () => cardinal(!cPos, cHorz),
		transferTo: s => cardinal(cPos == ((cHorz == s.xh) ? s.xp : s.yp), cHorz == s.xh),
		index: () => (cPos ? 0 : 1) + (cHorz ? 0 : 2),
		toString: () => [cPos, cHorz].map(b => b ? 'Y' : 'N').join(' ')
	}
}

// directionOf: (i: natural) => Cardinal
function directionOf(i) {
	return cardinal(i % 2 == 0, i % 4 < 2)
}

// var sArr = [
// 	square(true, true, true),
// 	square(false, true, true),
// 	square(true, false, true),
// 	square(false, false, true),
// 	square(true, true, false),
// 	square(false, true, false),
// 	square(true, false, false),
// 	square(false, false, false),
// ]
// console.log(sArr.forEach(s => console.log(s.asRelation(cardinal(true, true)).toString())))
// console.log(sArr.forEach(s => console.log(s.asRelation(cardinal(true, false)).toString())))

var plate1 = makePlate('A')
var plate2 = makePlate('B')
var plate3 = makePlate('C')
plate1.linkTo(plate2, square(true, true, true), directionOf(0), false) 		// 0000
plate2.linkTo(plate3, square(false, true, false), directionOf(2), false)	// 1011
plate3.linkTo(plate1, square(true, true, true), directionOf(3), false)		// 0001
plate1.linkTo(plate3, square(true, true, true), directionOf(2), false)		// 0001
plate2.linkTo(plate1, square(true, true, true), directionOf(1), false)		// 0000
plate3.linkTo(plate2, square(true, false, false), directionOf(0), false)	// 0110
console.log([plate1, plate2, plate3].map(x => x.getRelations().toString()))
// console.log(curr.getRelations().map(s => s == undefined ? " " : s.toString()))
var curr = plate1
// for (var i = 0; i < 4; i++) {
	console.log(curr.getOrient().toString())
	curr = curr.walkTo(directionOf(0)).nextPlate()
	console.log(curr.getOrient().toString())
	curr = curr.walkTo(directionOf(2)).nextPlate()
	console.log(curr.getOrient().toString())
	curr = curr.walkTo(directionOf(1)).nextPlate()
	console.log(curr.getOrient().toString())
	// curr = curr.walkTo(directionOf(3)).nextPlate()
// }
// for (var i = 0; i < 4; i++) {
// 	console.log(curr.getOrient().toString())
// 	curr = curr.walkTo(directionOf(2)).nextPlate()
// 	console.log(curr.getOrient().toString())
// 	curr = curr.walkTo(directionOf(0)).nextPlate()
// 	console.log(curr.getOrient().toString())
// 	curr = curr.walkTo(directionOf(3)).nextPlate()
// 	console.log(curr.getOrient().toString())
// 	curr = curr.walkTo(directionOf(1)).nextPlate()
// }

//	class Plate {
//		isEmpty: () => boolean, true if this plate has no content
// 		getOrient: () => Order8, returns this plate's current orientation
//		setOrient: (s: Order8) => Plate, overwrites the current orientation
//		getNeighbors: () => [Square]
//		getRelations: () => [Square]
//		walkTo: (direct: Cardinal) => { isPossible: () => boolean, nextPlate: () => Plate }
//		linkTo: (target: Plate, ori: Square, direct: Cardinal, bothWays: boolean) => undefined
// 		color: string, formatted to work with SVG.js
//	}

// makePlate: (colorString: string) => Plate
function makePlate(colorString) {
	var orient = square(true, true, true)
	var neighbors = new Array(4).fill({ isEmpty: () => true, color: 'black' })
	var relations = new Array(4).fill(undefined)
	return {
		isEmpty: () => false,
		getOrient: () => orient,
		setOrient: function(s) {
			orient = s
			return this
		},
		getNeighbors: () => neighbors,
		getRelations: () => relations,
		walkTo: function(direct) {
			var i = direct.transferTo(orient).index()
			return {
				isPossible: () => !neighbors[i].isEmpty(),
				nextPlate: () => neighbors[i].setOrient(orient.applyRelation(relations[i], direct))
			}
		},
		linkTo: function(target, ori, direct, bothWays) {
			var i = direct.index()
			neighbors[i] = target
			relations[i] = ori.asRelation(direct)
			if (bothWays) {
				target.linkTo(this, ori, direct.opposite().transferTo(ori), false)
			}
		},
		color: colorString
	}
}

// fillSlice: (d1: Cardinal, d2: Cardinal, depth: int, center: Plate, mx: num, my: num, plateSize: num, draw: SVG) => undefined
function fillSlice(d1, d2, depth, center, mx, my, plateSize, draw) {
	// treeDraw(center, 0, 0, Infinity, 0)

	// treeDraw: (plate: Plate, row: int, col: int, uppPrev: num, lowPrev: num) => undefined
	function treeDraw(plate, row, col, uppPrev, lowPrev) {
		if (row + col <= depth) {
			var xWest = mx + (plateSize * col)
			var ySouth = my + (plateSize * row)
			var xEast = xWest + plateSize
			var yNorth = ySouth - plateSize
			var upp = Math.min(ySouth / xWest, uppPrev)
			var low = Math.max(yNorth / xWest, lowPrev)
			var walk = plate.walkTo(d1)
			if (upp > low && walk.isPossible())  {
				var horzPlate = walk.nextPlate()
				drawPlate(horzPlate, upp, uppPrev, low, xWest, ySouth, xEast) // drawingOne
				treeDraw(horzPlate, row, col + 1, upp, low)
			}
			xEast = xWest // old value
			yNorth = ySouth // old value
			xWest = xEast - plateSize
			ySouth = yNorth + plateSize
			upp = Math.min(yNorth / xWest, uppPrev)
			low = Math.max(yNorth / xEast, lowPrev)
			walk = plate.walkTo(d2)
			if (upp > low && walk.isPossible()) {
				var vertPlate = walk.nextPlate()
				drawPlate(vertPlate, 1/low, 1/lowPrev, 1/upp, yNorth, xEast, ySouth) // reflect drawingOne over y = x
				treeDraw(vertPlate, row + 1, col, upp, low)
			}
		}
	}

	// drawPlate: (plate: Plate, m1: num, m1Prev: num, m2: num, ax: num, ay: num, bx: num) => undefined
	function drawPlate(plate, m1, m1Prev, m2, ax, ay, bx) {
		var axm1 = [ax, ax * m1]
		var aym1 = [ay / m1, ay]
		var axm2 = [ax, ax * m2]
		var aym2 = [ay / m2, ay]
		var bxm2 = [bx, bx * m2]
		var circ = [axm2, axm1]
		if (m1 < m1Prev) {
			circ.push(aym1)
		}
		if (bxm2[1] > ay) {
			circ.push(aym2)
		} else {
			circ.push([bx, ay], bxm2)
		}
		draw.polygon(circ).fill(plate.color)
	}

	drawPlate(makePlate('red'), 0.2, 0.1, 4, 140, 80, 180)

}
