import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

extension Kotlinx_datetimeLocalDate {
    func toDate() -> Date? {
        let components = DateComponents(year: Int(self.year), month: Int(self.month.ordinal+1), day: Int(self.dayOfMonth))
        return Calendar.current.date(from: components)
    }
}

struct DateField: View {
    @StateViewModel var formViewModel = FormViewModel()
    var label: String
    @Environment(\.colorScheme) var colorScheme

    var body: some View {
        HStack {
            Text(label)
            Spacer()
            Text(formViewModel.selectedDate.toDate()!, style: .date)
                .foregroundColor(.gray)
        }
        .padding()
        .cornerRadius(10)
        .foregroundColor(colorScheme == .light ? .black : .white)
    }
}

