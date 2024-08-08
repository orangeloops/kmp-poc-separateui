import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

func getYear(date: Date) -> Int32 {
    let calendar = Calendar.current
    let year = calendar.dateComponents([.year], from: date)
    return Int32(year.year ?? 2024)
}

func getMonth(date: Date) -> Int32 {
    let calendar = Calendar.current
    let month = calendar.dateComponents([.month], from: date)
    return Int32(month.month ?? 5)
}

func getDay(date: Date) -> Int32 {
    let calendar = Calendar.current
    let day = calendar.dateComponents([.day], from: date)
    return Int32(day.day ?? 1)
}

struct EntryForm: View {
    @EnvironmentObject var viewModel: TaskViewModel
    @State var formViewModel = FormViewModel()
    @Environment(\.presentationMode) var presentationMode
    @Binding var origin : Bool
    @State private var note: String = ""
    @State private var duration: TimeInterval = 0
    @State private var showAlert = false
    @State private var showDatePicker = false
    @Environment(\.colorScheme) var colorScheme
    var body: some View {
        if #available(iOS 16.0, *) {
            Form {
                Section{
                    NavigationLink(destination: ClientListView()) {
                        FormField(label: "Client", value: "client")
                        
                    }
                    .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 16))
                    
                    NavigationLink(destination: ProjectListView()) {
                        FormField(label: "Project", value: "project")
                    }
                    .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 16))
                    NavigationLink(destination: TaskListView()) {
                        FormField(label: "Task", value: "task")
                    }
                    .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 16))
                }
                
                Section{
                    Button(action: {
                        showDatePicker = true
                    }) {
                        DateField(label: "Date")
                    }
                    .sheet(isPresented: $showDatePicker) {
                        DatePickerView(isPres: $showDatePicker)
                            .presentationDetents([.medium, .fraction(0.5)])
                    }
                    .listRowInsets(EdgeInsets())
                }
                
                Section {
                    DurationField(duration: $duration)
                    .listRowInsets(EdgeInsets())
                }
                
                Section {
                    TextField("Write a note (optional)", text: $note)
                        .padding()
                        .cornerRadius(10)
                        .onChange(of: note) { newValue in
                            formViewModel.setNote(note: newValue)
                        }
                        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 16)) // Aquí ajusta el valor de `trailing` según lo necesites
                }
                                
                if (duration == 0){
                    Section {
                        Button(action: {
                            saveEntry()
                        }) {
                            HStack {
                                Image(systemName: "play.fill")
                                    .font(.footnote)
                                    .foregroundColor(.white)
                                Text("Start timer")
                                    .font(.footnote)
                                    .fontWeight(.medium)
                                    .foregroundColor(.white)
                            }
                            .frame(maxWidth: .infinity) // Hacer que el contenido del botón ocupe todo el ancho
                            .padding()
                        }
                        .background(
                            RoundedRectangle(cornerRadius: 10)
                                .fill(Color(red: 0.0, green: 0.5, blue: 0.0)) // Verde más oscuro personalizado
                                .frame(maxWidth: .infinity)
                        )
                        .listRowInsets(EdgeInsets())
                    }
                    
                } else {
                    Section {
                        Button(action: {
                            saveEntry()
                        }) {
                            Text("Save entry")
                                .font(.footnote)
                                .fontWeight(.medium)
                                .foregroundColor(.white)
                        }
                        .frame(maxWidth: .infinity) // Hacer que el contenido del botón ocupe todo el ancho
                        .padding()
                        .background(
                            RoundedRectangle(cornerRadius: 10)
                                .fill(Color(red: 0.0, green: 0.5, blue: 0.0)) // Verde más oscuro personalizado
                                .frame(maxWidth: .infinity)
                        )
                        .listRowInsets(EdgeInsets())
                    }
                }
            }
            .navigationTitle("New time entry")
            .navigationBarTitleDisplayMode(.inline)
            .alert(isPresented: $showAlert) {
                Alert(title: Text("Entry Saved"), message: Text("Your time entry has been saved."), dismissButton: .default(Text("OK")))
            }
            .background(colorScheme == .light ? Color(red: 1.0, green: 0.98, blue: 0.94) : .black)
            .onAppear(){
                origin = true
            }
            .scrollContentBackground(.hidden)
        } else {
            // Fallback on earlier versions
        }
    }


    private func saveEntry() {
        viewModel.addTask(title: formViewModel.selectedProject + " (" + formViewModel.selectedClient + ")", category: formViewModel.selectedTask, details: formViewModel.note, hours: timeString(from: formViewModel.selectedDuration), year: getYear(date: formViewModel.selectedDate.toDate()!), month: getMonth(date: formViewModel.selectedDate.toDate()!), day: getDay(date: formViewModel.selectedDate.toDate()!))
        presentationMode.wrappedValue.dismiss()
        origin = true
    }
    
    private func startEntry() {
        viewModel.addPendingTask(title: formViewModel.selectedProject + " (" + formViewModel.selectedClient + ")", category: formViewModel.selectedTask, details: formViewModel.note)
        presentationMode.wrappedValue.dismiss()
        origin = true
    }

    private func timeString(from interval: TimeInterval) -> String {
        let hours = Int(interval) / 3600
        let minutes = (Int(interval) % 3600) / 60
        return String(format: "%02d:%02d", hours, minutes)
    }
}
