import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct TimeView: View {
    @EnvironmentObject var viewModel: TaskViewModel
    @EnvironmentObject var weekViewModel: WeekViewModel
    @EnvironmentObject var formViewModel: FormViewModel
    @Binding var showTabView: Bool
    @Binding var navigate : Bool
    @Binding var deleteView : Bool
    @State var origin: Bool
    @State private var showActionSheet = false
    @Environment(\.colorScheme) var colorScheme
    
    init(showTab: Binding<Bool>, from: Bool, nav: Binding<Bool>, del: Binding<Bool>) {
        _showTabView = showTab
        _navigate = nav
        _origin = State(initialValue: from)
        _deleteView = del
    }

    var body: some View {
        NavigationView {
            VStack(alignment: .leading) {
                HStack {
                    Text("Time")
                        .font(.title)
                        .fontWeight(.medium)
                        .padding(.leading, 20)
                    Spacer()
                    Button(action: {
                        showActionSheet = true
                    }) {
                        Image(systemName: "ellipsis")
                            .imageScale(.large)
                            .foregroundColor(colorScheme == .dark ? .white : .black)
                    }
                    .padding(.trailing, 20)
                }
                .actionSheet(isPresented: $showActionSheet) {
                    ActionSheet(title: Text(""), buttons: [
                        .default(Text("Today's team timers")),
                        .cancel()
                    ])
                }
                
                HeaderView()

                CalendarView()
                                
                VStack {
                    Divider()
                        .background(Color.gray.opacity(0.3))

                    HStack{
                        if #available(iOS 16.0, *) {
                            Text("Week total: " + weekViewModel.selectedWeek.hours)
                                .font(.footnote)
                                .padding()
                                .fontWeight(.medium)
                        } else {
                            Text("Week total: " + weekViewModel.selectedWeek.hours)
                                .font(.footnote)
                                .padding()
                        }
                
                        
                        Spacer()
                        
                        Button(action: {}){
                            Text("Submit")
                                .font(.footnote)
                                .fontWeight(.medium)
                                .padding()
                                .foregroundColor(.blue)
                        }
                    }
                                        
                    TasksListView(origin: $origin, deleteView: $deleteView, showTabView: $showTabView)

                    Spacer()
                    
                    VStack {
                        ZStack {
                            NavigationLink(destination: EntryForm(origin: $origin).environmentObject(self.viewModel).environmentObject(self.formViewModel), isActive: $navigate) {
                                EmptyView()
                            }
                            .hidden()
                            
                            Button(action: {
                                showTabView = false
                                navigate = true
                            }) {
                                HStack {
                                    Image(systemName: "plus")
                                        .font(.footnote)
                                        .foregroundColor(.white)
                                    Text("Track time")
                                        .font(.footnote)
                                        .foregroundColor(.white)
                                        .fontWeight(.medium)
                                }
                                .frame(maxWidth: .infinity) // Hacer que el contenido del botón ocupe todo el ancho
                                .padding()
                            }
                            .background(
                                RoundedRectangle(cornerRadius: 10)
                                    .fill(Color(red: 0.0, green: 0.5, blue: 0.0)) // Verde más oscuro personalizado
                                    .frame(maxWidth: .infinity)
                            )
                            .padding(.horizontal, 10)
                            .padding(.bottom, 20)
                        }
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity) // Hacer que el VStack ocupe todo el ancho disponible
                .background(colorScheme == .light ? Color(red: 1.0, green: 0.98, blue: 0.94) : .black) // Color crema
                .cornerRadius(10)
                .padding(.vertical, 10)
            }
            .navigationBarHidden(true)
            .onAppear {
                showTabView = origin
                formViewModel.setSelectedDuration(duration: 0)
            }
        }
        .navigationViewStyle(StackNavigationViewStyle()) // Asegura que se use la navegación en estilo stack
    }
}

struct DayOfWeek: Identifiable {
    var id: String { day } // Conformar a Identifiable
    let day: String
    let number: String
    let hours: String
    let month: String
}
