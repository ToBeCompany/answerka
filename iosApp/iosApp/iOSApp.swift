import SwiftUI
import shared

@main
struct iOSApp: App {
    init(){
        KoinModuleKt.doInitKoin { K in
            
        }
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
