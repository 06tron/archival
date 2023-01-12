//
//  Orbiter.swift
//  Isometric Space
//
//  Created by Matthew Richardson on 8/7/20.
//  Copyright © 2020 Matthew Richardson. All rights reserved.
//

class Orbiter<T> {
    
    var x, y, z: SpatialAxis<T>
    var horizontal, vertical, hidden: DisplayAxis<T>
    var undo: Swipe

    init() {
        x = SpatialAxis(position: Signum.neutral)
        y = SpatialAxis(position: Signum.neutral)
        z = SpatialAxis(position: Signum.negative)
        horizontal = DisplayAxis(axis: x, flipped: false)
        vertical = DisplayAxis(axis: y, flipped: false)
        hidden = DisplayAxis(axis: z, flipped: false)
        undo = Swipe(latitudinal: true, northeast: true)
    }
    
    func swipeDisplay(direction: Swipe) {
        if direction == undo || !onCorner {
            let target: DisplayAxis = direction.latitudinal ? vertical : horizontal
            let moveTowards: Signum = target.flipped == direction.northeast ? .positive : .negative
            if target.axis.position == moveTowards {
                let copy = DisplayAxis(target)
                target.axis = hidden.axis
                target.flipped = hidden.flipped == direction.northeast
                hidden = DisplayAxis(axis: copy.axis, flipped: copy.flipped != direction.northeast)
                swipeDisplay(direction: direction)
            } else {
                target.axis.position += moveTowards
            }
            undo = direction.opposite
        }
    }
    
    var onCorner: Bool {
        state == 0
    }
    
    var state: Int {
        [x, y, z].filter { axis in
            axis.position == .neutral
        }.count
    }

}
