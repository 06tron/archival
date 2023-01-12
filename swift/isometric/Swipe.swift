//
//  Swipe.swift
//  Isometric Space
//
//  Created by Matthew Richardson on 8/13/20.
//  Copyright © 2020 Matthew Richardson. All rights reserved.
//

struct Swipe: Equatable {
    // Represents the direction of a swipe gesture
    
    let latitudinal, northeast: Bool
    
    var opposite: Swipe {
        Swipe(latitudinal: latitudinal, northeast: !northeast)
    }
    
}
