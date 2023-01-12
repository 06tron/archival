//
//  ContentView.swift
//  Isometric Space
//
//  Created by Matthew Richardson on 8/7/20.
//  Copyright © 2020 Matthew Richardson. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    
    static let orb = Orbiter<CGColor>(r: UIColor.red, g: UIColor.green, b: UIColor.blue)
    @State var display = orb.image()
    @State var debugStr = orb.debugStr
    
    var body: some View {
        ZStack {
            Image(uiImage: display)
                .gesture(DragGesture()
                    .onEnded { value in
                        let v = value.translation
                        let swipe: Swipe
                        if abs(v.height) > abs(v.width) {
                            // negative translation height means northward swipe
                            swipe = Swipe(latitudinal: true, northeast: v.height < 0)
                        } else {
                            // positive translation width means eastward swipe
                            swipe = Swipe(latitudinal: false, northeast: v.width > 0)
                        }
                        ContentView.orb.swipeDisplay(direction: swipe)
                        self.display = ContentView.orb.image()
                        self.debugStr = ContentView.orb.debugStr
                    }
                )
            Text(debugStr)
        }
    }
    
}

extension Orbiter where T == CGColor {
    
    convenience init(r: UIColor, g: UIColor, b: UIColor) {
        self.init()
        x.identifier = r.cgColor
        y.identifier = g.cgColor
        z.identifier = b.cgColor
    }
    
    static let imgSize = CGSize(width: 512, height: 1024)
    static let imgCenter = CGPoint(x: imgSize.width/2, y: imgSize.height/2)
    static let imgTop = CGPoint(x: imgSize.width/2, y: 0)
    static let imgBottom = CGPoint(x: imgSize.width/2, y: imgSize.height)
    static let imgLeft = CGPoint(x: 0, y: imgSize.height/2)
    static let imgRight = CGPoint(x: imgSize.width, y: imgSize.height/2)
    
    func image() -> UIImage {
        return UIGraphicsImageRenderer(size: Orbiter.imgSize).image { ctx in
            
            let c = ctx.cgContext
            
            if undo.latitudinal || !onCorner {
                c.move(to: Orbiter.imgCenter)
                c.setStrokeColor(vertical.axis.identifier)
                c.addLine(to: vertical.flipped ? Orbiter.imgBottom : Orbiter.imgTop)
                c.strokePath()
            }
            
            if !undo.latitudinal || !onCorner {
                c.move(to: Orbiter.imgCenter)
                c.setStrokeColor(horizontal.axis.identifier)
                c.addLine(to: horizontal.flipped ? Orbiter.imgLeft : Orbiter.imgRight)
                c.strokePath()
            }
            
            c.move(to: Orbiter.imgCenter)
            c.setStrokeColor(hidden.axis.identifier)
            if horizontal.axis.position != .neutral && vertical.axis.position == .neutral {
                c.addLine(to: (horizontal.flipped == (horizontal.axis.position == .negative)) == hidden.flipped ? Orbiter.imgRight : Orbiter.imgLeft)
            }
            if vertical.axis.position != .neutral && horizontal.axis.position == .neutral {
                c.addLine(to: (vertical.flipped == (vertical.axis.position == .negative)) == hidden.flipped ? Orbiter.imgTop : Orbiter.imgBottom)
            }
            c.strokePath()
            
        }
    }
    
    var debugStr: String { """
        (\(x.position), \(y.position), \(z.position)),
        last swipe: \(["left", "right", "down", "up"][(undo.opposite.latitudinal ? 2 : 0) + (undo.opposite.northeast ? 1 : 0)])),
        state: \(state)
        """
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
