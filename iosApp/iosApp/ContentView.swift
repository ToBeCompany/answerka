import SwiftUI
import shared

struct ContentView: View {
    
    @State private var players : [String] = []
    @State var newPlayer : String = ""
    
	var body: some View {
        VStack{
            NavigationView {
                List {
                    ForEach (players, id: \.self){ player in
                        Text(player)
                    }
                    .onDelete { players.remove(atOffsets: $0) }
                }
                .navigationTitle("Players")
                .toolbar {
                    EditButton()
                }
            }
            Button(action: {
                
            }){
                Text("Play!")
            }
            TextField("Player name", text: $newPlayer)
        
            Button(action : {
                if (!newPlayer.isEmpty){
                    players.append(newPlayer)
                    newPlayer = ""
                }
            }){
                Text("Add Player")
            }
            Spacer()
        }
    }
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView()
	}
}

struct Players: View {
    @State private var fruits = [
        "Apple",
        "Banana",
        "Papaya",
        "Mango"
    ]

    var body: some View {
        return NavigationView {
            List {
                ForEach(fruits, id: \.self) { fruit in
                    Text(fruit)
                }
                .onDelete { fruits.remove(atOffsets: $0) }
                .onMove { fruits.move(fromOffsets: $0, toOffset: $1) }
            }
            .navigationTitle("Fruits")
            .toolbar {
                EditButton()
            }
        }
    }
}
