//
//  SpatialAxis.swift
//  Isometric Space
//
//  Copyright © 2020 Matthew Richardson. All rights reserved.
//

class SpatialAxis<T> {
    // Represents an coordinate axis of some space
    // Stored is a position along this axis: positive, negative, or neutral (zero)
    // Also an optional property of type T, used to identify this axis
    
    var position: Signum
    var identifier: T!
    
    init(position: Signum) {
        self.position = position
        self.identifier = nil
    }
    
}
