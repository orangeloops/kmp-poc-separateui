import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

extension String {
    func extractTextBetweenParentheses() -> String? {
        guard let start = self.firstIndex(of: "("),
              let end = self.firstIndex(of: ")"),
              start < end else { return nil }
        let startIndex = self.index(after: start)
        return String(self[startIndex..<end])
    }
}

extension String {
    func extractTextBeforeParentheses() -> String {
        guard let start = self.firstIndex(of: "(") else { return self }
        let endIndex = self.index(before: start)
        return String(self[..<endIndex])
    }
}

struct DeleteForm: View {
    @State private var isDataLoaded = false
    @EnvironmentObject var viewModel : TaskViewModel
    @State var formViewModel = FormViewModel()
    @Binding var origin : Bool
    @Environment(\.presentationMode) var presentationMode
    private var task: Models.Task
    @State private var duration: TimeInterval = 0
    @State var note: String
    @State private var showAlert = false
    @State private var showDatePicker = false
    @Environment(\.colorScheme) var colorScheme
    private var id: String
    
    func createDate(year: Int, month: Int, day: Int) -> Date? {
        var dateComponents = DateComponents()
        dateComponents.year = year
        dateComponents.month = month
        dateComponents.day = day
        
        let calendar = Calendar.current
        return calendar.date(from: dateComponents)
    }
    
    func timeStringToTimeInterval(_ timeString: String) -> TimeInterval? {
        let components = timeString.split(separator: ":")
        guard components.count == 2,
              let hours = Int(components[0]),
              let minutes = Int(components[1]) else {
            return nil
        }
        
        return TimeInterval((hours * 3600) + (minutes * 60))
    }
    
    init(task: Models.Task, origin: Binding<Bool>) {
        self.task = task
        _origin = origin
        id = task.id
        _note = State(initialValue: task.details)
        if (task.inProgress){
            _duration = State(initialValue: 0)
        } else {
            _duration = State(initialValue: timeStringToTimeInterval(task.hours) ?? 0)
        }
    }

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
                    DeleteDurationField()
                    .listRowInsets(EdgeInsets())
                }
                
                Section {
                    TextField("Write a note (optional)", text: $note)
                        .onChange(of: note) { newValue in
                            formViewModel.setNote(note: newValue)
                        }
                        .padding()
                        .cornerRadius(10)
                        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 16)) // Aquí ajusta el valor de `trailing` según lo necesites
                }
                
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
            .navigationTitle("Edit time entry")
            .navigationBarTitleDisplayMode(.inline)
            .navigationBarItems(trailing: Button(action: {
                showAlert = true
                }) {
                    Text("Delete")
                        .foregroundColor(Color(red: 0.7, green: 0.0, blue: 0.0))
                        .font(.footnote)
                        .fontWeight(.medium)
                })
            .alert(isPresented: $showAlert) {
                Alert(
                    title: Text("Delete Time Entry"),
                    message: Text("Delete this Time entry permanently?"),
                    primaryButton: .destructive(Text("OK")) {
                        viewModel.deleteTask(taskId: id)
                        presentationMode.wrappedValue.dismiss()
                    },
                    secondaryButton: .cancel()
                )
            }
            .background(colorScheme == .light ? Color(red: 1.0, green: 0.98, blue: 0.94) : .black)
            .onAppear(){
                if (!isDataLoaded){
                    formViewModel.setSelectedClient(client: task.title.extractTextBetweenParentheses()!)
                    formViewModel.setSelectedProject(project: task.title.extractTextBeforeParentheses())
                    formViewModel.setSelectedTask(task: task.category)
                    if (task.inProgress){
                        formViewModel.setSelectedDuration(duration: 0)
                    } else {
                        formViewModel.setSelectedDuration(duration: timeStringToTimeInterval(task.hours) ?? 0)
                    }
                    formViewModel.setSelectedDate(date: (createDate(year: Int(task.date.year), month: Int(task.date.monthNumber), day: Int(task.date.dayOfMonth))?.toLocalDate())!)
                    formViewModel.setNote(note: task.details)
                    isDataLoaded = true
                }
                origin = true
            }
            .scrollContentBackground(.hidden)
        } else {
            // Fallback on earlier versions
        }
    }


    private func saveEntry() {
        // Simulate saving the entry
        viewModel.editTask(taskId: id, title: formViewModel.selectedProject + " (" + formViewModel.selectedClient + ")", category: formViewModel.selectedTask, details: formViewModel.note, hours: timeString(from: formViewModel.selectedDuration), year: getYear(date: formViewModel.selectedDate.toDate()!), month: getMonth(date: formViewModel.selectedDate.toDate()!), day: getDay(date: formViewModel.selectedDate.toDate()!))
        presentationMode.wrappedValue.dismiss()
        origin = true
    }

    private func timeString(from interval: TimeInterval) -> String {
        let hours = Int(interval) / 3600
        let minutes = (Int(interval) % 3600) / 60
        return String(format: "%02d:%02d", hours, minutes)
    }
}
