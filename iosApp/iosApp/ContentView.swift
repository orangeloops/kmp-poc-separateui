import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct ContentView: View {
    @StateViewModel var viewModel = TaskViewModel()
    @StateViewModel var weekViewModel = WeekViewModel()
    @StateViewModel var formViewModel = FormViewModel()
    @State var navigate = false
    @State var deleteView = false
    @State private var showTabView: Bool = true
    @State private var selectedTabIndex: Int = 1 // 1 es el índice de TimeView

    var body: some View {
        if (showTabView){
            TabView(selection: $selectedTabIndex) {
                HomeView()
                    .tabItem {
                        Image(systemName: "house")
                        Text("Home")
                    }
                    .tag(0)
    
                TimeView(showTab: $showTabView, from: true, nav: $navigate, del: $deleteView)
                    .tabItem {
                        Image(systemName: "clock")
                        Text("Time")
                    }
                    .tag(1)
                    .environmentObject(viewModel)
                    .environmentObject(weekViewModel)
                    .environmentObject(formViewModel)
    
                ReportsView()
                    .tabItem {
                        Image(systemName: "doc.text")
                        Text("Reports")
                    }
                    .tag(2)
    
                AccountView()
                    .tabItem {
                        Image(systemName: "hexagon")
                        Text("Account")
                    }
                    .tag(3)
            }.accentColor(.orange)
        } else {
            TimeView(showTab: $showTabView, from: false, nav: $navigate, del: $deleteView).environmentObject(viewModel)
                .environmentObject(weekViewModel)
                .environmentObject(formViewModel)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

