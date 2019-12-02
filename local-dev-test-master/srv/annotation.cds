// using CatalogService as srv from './cat-service';

// annotate srv.Books with {
//     title
//         @title: 'Book Title'
//         @Common.FieldControl: #Mandatory; // 强制添加
//     stock
//         @title: 'Book Stock';
//     author
//         @Common:{
//             Label: 'Author',
//             Text: {$value: author.name, "@UI.TextArrangement": #TextOnly}, ValueList: {entity: 'Authors'}
//         };
// };


// annotate srv.Authors with {
//     // ID
//     //    @title: 'Author Id';
//     //    // @Common.FieldControl: #Mandatory; // 强制添加
//     name
//         @title: 'Author name';
// };


// annotate srv.Books with @(
//     UI.LineItem:[
//         {$Type: 'UI.DataField', Value: title, Label: 'Book Title'},
//         {$Type: 'UI.DataField', Value: stock, Label: 'Book Stock'},
//     ],
//     UI.HeaderInfo: {
//         title: {Value: title},
//         stock: {Value: stock},
//         TypeName: 'TypeInHeaderInfo',
//         TypeNamePlural: 'TypesInListReport'
//     },

//     UI.HeaderFacets: [
//         {$Type: 'UI.ReferenceFacet', Target: '@UI.FieldGroup#HeaderInfo'},
//     ],

//     // // UI.Identification: [
//     // //       {$Type: 'UI.DataFieldForAction', Label: 'Set To Use', Action: 'CollaborationTypeService.CollaborationType/setToUse'}
//     // // ],

//     UI.Facets:[{
//         $Type: 'UI.CollectionFacet',
//         Facets:[
//             {$Type:'UI.ReferenceFacet', Label:'Book Info', Target:'@UI.FieldGroup#BookInfo'},
//             {$Type:'UI.ReferenceFacet', Label:'Author Info', Target:'@UI.FieldGroup#AuthorInfo'}

//         ],
//         Label:'The Info of the Author'
//     }],

//     UI.FieldGroup#HeaderInfo:{
//         Label:'Header Info',
//         Data:[{
//             $Type:'UI.DataField', Value:author.name, Label:'The author name is: '
//         }]
//     },

//     UI.FieldGroup#BookInfo:{
//         Label:'Book Info',
//         Data:[
//             {$Type:'UI.DataField', Value:title},
//             {$Type:'UI.DataField', Value:stock},
//             // {$Type:'UI.DataField', Value:author.name},
//         ]
//     },

//     UI.FieldGroup#AuthorInfo:{
//         Label:'Author',
//         Data:[
//             {$Type:'UI.DataField', Value:author.name},
//             {$Type:'UI.DataField', Value:author.ID}
//         ]
//     }
// );

using CatalogService from './cat-service';

annotate CatalogService.Authors with {
    name
        @title : 'Author';
};

annotate CatalogService.Books with {
    ID
        @title : 'Id';
    title
        @title : 'Title';
    stock
        @title : 'Stock';
    author
        @Common.Text: "author/name"
        @title : 'Author'
        @sap.value.list: 'fixed-values'
        @Common.ValueList: {
            CollectionPath: 'Authors',
            Label: 'Authors',
            SearchSupported: 'true',
            Parameters: [
                { $Type: 'Common.ValueListParameterOut', LocalDataProperty: 'author_ID', ValueListProperty: 'ID'},
                { $Type: 'Common.ValueListParameterDisplayOnly', ValueListProperty: 'name'},
            ]
        };
};

annotate CatalogService.Books with @(
    UI.LineItem: [
        {$Type: 'UI.DataField', Value: ID},
        {$Type: 'UI.DataField', Value: title},
        {$Type: 'UI.DataField', Value: stock},
        // {$Type: 'UI.DataField', Value: "author/name"},
    ],

    UI.HeaderInfo: {
        Title: { Value: title },
        TypeName:'Book',
            TypeNamePlural:'Books'
    },

    UI.Identification:
    [
        {$Type: 'UI.DataField', Value: ID},
        {$Type: 'UI.DataField', Value: title},
        {$Type: 'UI.DataField', Value: "author/name"},
        {$Type: 'UI.DataFieldForAction', Label: 'Set to use', Action: 'CatalogService/set2use'},
    ],

    UI.Facets:
    [
        {
            $Type:'UI.CollectionFacet',
            Facets: [
                        { $Type:'UI.ReferenceFacet', Label: 'General Info', Target: '@UI.Identification' }
                    ],
            Label:'Book Details',
        },
        {$Type:'UI.ReferenceFacet', Label: 'Orders', Target: 'orders/@UI.LineItem'},
    ]
);

annotate CatalogService.Orders with {
    ID
        @title: 'Order'
        @Common.FieldControl: #ReadOnly;
    book
        @title: 'Book'
        @Common.FieldControl: #ReadOnly;
    createdBy
        @title: 'Buyer'
        @Common.FieldControl: #ReadOnly;
    createdAt
        @title: 'Date'
        @Common.FieldControl: #ReadOnly;
    amount
        @title: 'Amount'
        @Common.FieldControl: #ReadOnly;
};

annotate CatalogService.Orders with @(
    UI.LineItem: [
        {$Type: 'UI.DataField', Value: ID},
        {$Type: 'UI.DataField', Value: book},
        {$Type: 'UI.DataField', Value: buyer},
        {$Type: 'UI.DataField', Value: date},
        {$Type: 'UI.DataField', Value: amount}
    ],

    UI.HeaderInfo: {
        Title: { Value: ID },
        TypeName:'Order',
            TypeNamePlural:'Orders'
    },

    UI.Identification:
    [
        {$Type: 'UI.DataField', Value: ID},
        {$Type: 'UI.DataField', Value: book},
        {$Type: 'UI.DataField', Value: buyer},
        {$Type: 'UI.DataField', Value: date},
        {$Type: 'UI.DataField', Value: amount}
    ],
    UI.Facets:
    [
        {
            $Type:'UI.CollectionFacet',
            Facets: [
                        { $Type:'UI.ReferenceFacet', Label: 'Assignment', Target: '@UI.Identification' }
                    ],
            Label:'Orders',
        }
    ]
);