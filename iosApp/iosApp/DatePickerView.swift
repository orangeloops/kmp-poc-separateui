import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

extension Date {
    func toLocalDate() -> Kotlinx_datetimeLocalDate? {
        let calendar = Calendar.current
        let components = calendar.dateComponents([.year, .month, .day], from: self)
        guard let year = components.year, let monthInt = components.month, let day = components.day else {
            return nil
        }
        return Kotlinx_datetimeLocalDate(year: Int32(year), monthNumber: Int32(monthInt), dayOfMonth: Int32(day))
    }
}

struct DatePickerView: View {
    @StateViewModel var formViewModel = FormViewModel()
    @State var date: Date
    @Binding var isPresented: Bool
    
    init(isPres: Binding<Bool>){
        _date = State(initialValue: Date())
        _isPresented = isPres
    }

    var body: some View {
            VStack {
                DatePicker("Select a date", selection: $date, displayedComponents: .date)
                    .datePickerStyle(GraphicalDatePickerStyle())
                    .padding()
                    .onAppear {
                        if let initialDate = formViewModel.selectedDate.toDate() {
                            self.date = initialDate
                        }
                    }
                    .onChange(of: date) { newValue in
                        if let localDate = newValue.toLocalDate() {
                            formViewModel.setSelectedDate(date: localDate)
                        }
                    }
                Button("Done") {
                    isPresented = false
                }
                .padding()
            }
        }
}

