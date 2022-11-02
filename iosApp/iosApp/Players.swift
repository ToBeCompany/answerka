//
//  Players.swift
//  iosApp
//
//  Created by Arbonik on 11.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class DemoData : ObservableObject{
    @Published var userCount : Int = 0
    init() {
        updateData()
    }
    
    func updateData(){
        
    }
}
