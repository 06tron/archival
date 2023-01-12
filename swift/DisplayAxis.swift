//
//  DisplayAxis.swift
//  Isometric Space
//
//  Copyright © 2020 Matthew Richardson. All rights reserved.
//

class DisplayAxis<T> {
    
    var axis: SpatialAxis<T>
    var flipped: Bool
    
    init(axis: SpatialAxis<T>, flipped: Bool) {
        self.axis = axis
        self.flipped = flipped
    }
    
    convenience init(_ other: DisplayAxis) {
        self.init(axis: other.axis, flipped: other.flipped)
    }
    
}
