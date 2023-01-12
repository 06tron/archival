//
//  Signum.swift
//  Isometric Space
//
//  Created by Matthew Richardson on 8/7/20.
//  Copyright © 2020 Matthew Richardson. All rights reserved.
//

enum Signum {
    
    case negative, neutral, positive
    
    static func + (a: Signum, b: Signum) -> Signum {
        return (a == b || b == neutral) ? a : (a == neutral) ? b : neutral
    }
    
    static func += (a: inout Signum, b: Signum) {
        a = a + b
    }
    
}
